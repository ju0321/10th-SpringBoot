package com.umc.umcmission.domain.review.service;

import com.umc.umcmission.domain.review.dto.ReviewReqDTO.CreateReviewReq;
import com.umc.umcmission.domain.review.dto.ReviewResDTO.CreateReviewRes;
import com.umc.umcmission.domain.review.dto.ReviewResDTO.GetReviewListRes;
import com.umc.umcmission.domain.review.dto.ReviewResDTO.GetReviewRes;
import com.umc.umcmission.domain.review.dto.ReviewResDTO.MyReviewPreview;
import com.umc.umcmission.domain.review.dto.ReviewResDTO.Pagination;
import com.umc.umcmission.domain.review.entity.Review;
import com.umc.umcmission.domain.review.exception.ReviewException;
import com.umc.umcmission.domain.review.exception.code.ReviewErrorCode;
import com.umc.umcmission.domain.review.repository.ReviewRepository;
import com.umc.umcmission.domain.store.entity.Store;
import com.umc.umcmission.domain.store.exception.code.StoreErrorCode;
import com.umc.umcmission.domain.store.repository.StoreRepository;
import com.umc.umcmission.domain.user.entity.User;
import com.umc.umcmission.domain.user.exception.code.UserErrorCode;
import com.umc.umcmission.domain.user.repository.UserRepository;
import com.umc.umcmission.global.apiPayload.exception.ProjectException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final UserRepository userRepository;
  private final StoreRepository storeRepository;

  //리뷰 작성
  @Transactional
  public CreateReviewRes createReview(Long storeId, CreateReviewReq request) {
    //0. user & Store 조회
    User user = userRepository.findById(request.userId())
        .orElseThrow(() -> new ProjectException(UserErrorCode.USER_NOT_FOUND));

    Store store = storeRepository.findById(storeId)
        .orElseThrow(() -> new ProjectException(StoreErrorCode.STORE_NOT_FOUND));


    //1. 리뷰 객체 빌더로 생성
    Review review = Review.builder()
        .user(user)
        .store(store)
        .rating(request.rating())
        .content(request.content())
        .imageUrl(request.imageUrl())
        .build();

    //2. 레파지토리로 save
    Review saveReview = reviewRepository.save(review);

    //3. Response형태로 return하기
    return toCreateReviewRes(saveReview);
  }

  //리뷰 조회
  @Transactional
  public GetReviewRes getReview(Long storeId, Long reviewId) {
    //1. storeId, reviewId 있는지 조회
    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new ProjectException(ReviewErrorCode.REVIEW_NOT_FOUND));
    if (!review.getStore().getId().equals(storeId)) {
      throw new ProjectException(ReviewErrorCode.REVIEW_NOT_FOUND);
    }
    //2. 리뷰 객체 받아서 response형태로 리턴
    return toGetReviewRes(review);
  }

  //리뷰 리스트 조회
  @Transactional
  public GetReviewListRes getReviews(Long storeId){
    // 1. 가게 존재 여부 확인
    if (!storeRepository.existsById(storeId)) {
      throw new ProjectException(StoreErrorCode.STORE_NOT_FOUND);
    }

    // 2. 해당 가게의 리뷰 목록 조회
    List<GetReviewRes> reviewList = reviewRepository.findByStoreId(storeId)
        .stream()
        .map(this::toGetReviewRes)
        .toList();

    return GetReviewListRes.builder()
        .reviewList(reviewList)
        .build();
  }
  // 내가 생성한 리뷰 목록 조회 (커서 기반 페이지네이션)
  @Transactional(readOnly = true)
  public Pagination<MyReviewPreview> getMyReviews(Long userId, Integer pageSize, String cursor, String query) {
    if (!userRepository.existsById(userId)) {
      throw new ProjectException(UserErrorCode.USER_NOT_FOUND);
    }

    // 1. 페이지 정보로 PageRequest 만들기
    Pageable pageable = PageRequest.of(0, pageSize);

    Slice<Review> reviewList;
    String nextCursor;

    // 2. 커서 유무 확인
    if (!cursor.equals("-1")) {
      // 2a. 커서 있는 경우 → 커서 분리 & 타입 변환
      String[] cursorSplit = cursor.split(":");
      switch (query.toLowerCase()) {
        case "id":
          Long prevCursor = Long.valueOf(cursorSplit[0]);
          Long idCursor = Long.parseLong(cursorSplit[1]);
          // 가게 내 리뷰들 조회 & where절에 커서값 기입
          reviewList = reviewRepository.findReviewsByUserIdAndIdLessThanOrderByIdDesc(userId, idCursor, pageable);
          break;
        case "rating":
          Integer lastRating = Integer.valueOf(cursorSplit[0]);
          Long lastId = Long.parseLong(cursorSplit[1]);
          reviewList = reviewRepository.findReviewsByUserIdWithRatingCursor(userId, lastRating, lastId, pageable);
          break;
        default:
          throw new ReviewException(ReviewErrorCode.QUERY_NOT_VALID);
      }
    } else {
      // 2b. 커서 없이 조회
      switch (query.toLowerCase()) {
        case "id":
          reviewList = reviewRepository.findReviewsByUserIdOrderByIdDesc(userId, pageable);
          break;
        case "rating":
          reviewList = reviewRepository.findReviewsByUserIdOrderByRatingDesc(userId, pageable);
          break;
        default:
          throw new ReviewException(ReviewErrorCode.QUERY_NOT_VALID);
      }
    }

    // 3. 다음 커서 계산
    Review lastReview = reviewList.getContent().isEmpty() ? null : reviewList.getContent().getLast();
    if (lastReview != null) {
      // ID:ID 형태 (id 순) 또는 lastRating:lastId 형태 (rating 순)
      nextCursor = query.equalsIgnoreCase("rating")
          ? lastReview.getRating() + ":" + lastReview.getId()
          : lastReview.getId() + ":" + lastReview.getId();
    } else {
      nextCursor = null;
    }

    // 4. 응답 DTO로 포장하기
    return toPagination(reviewList, pageSize, nextCursor);
  }

  private Pagination<MyReviewPreview> toPagination(Slice<Review> reviewSlice, Integer pageSize, String nextCursor) {
    List<MyReviewPreview> data = reviewSlice.getContent().stream()
        .map(r -> MyReviewPreview.builder()
            .reviewId(r.getId())
            .storeName(r.getStore().getStoreName())
            .rating(r.getRating())
            .content(r.getContent())
            .createdAt(r.getCreatedAt())
            .build())
        .toList();

    return Pagination.<MyReviewPreview>builder()
        .data(data)
        .pageSize(pageSize)
        .nextCursor(nextCursor)
        .hasNext(reviewSlice.hasNext())
        .build();
  }

  private CreateReviewRes toCreateReviewRes(Review review) {
    return CreateReviewRes.builder()
        .reviewId(review.getId())
        .rating(review.getRating())
        .content(review.getContent())
        .createdAt(review.getCreatedAt())
        .build();
  }

  private GetReviewRes toGetReviewRes(Review review) {
    return GetReviewRes.builder()
        .reviewId(review.getId())
        .nickname(review.getUser().getNickname())
        .rating(review.getRating())
        .content(review.getContent())
        .imageUrl(review.getImageUrl())
        .createdAt(review.getCreatedAt())
        .build();
  }
}