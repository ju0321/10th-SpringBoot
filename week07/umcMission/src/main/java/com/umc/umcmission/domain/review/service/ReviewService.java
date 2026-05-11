package com.umc.umcmission.domain.review.service;

import com.umc.umcmission.domain.review.dto.ReviewReqDTO.CreateReviewReq;
import com.umc.umcmission.domain.review.dto.ReviewResDTO.CreateReviewRes;
import com.umc.umcmission.domain.review.dto.ReviewResDTO.GetReviewListRes;
import com.umc.umcmission.domain.review.dto.ReviewResDTO.GetReviewRes;
import com.umc.umcmission.domain.review.dto.ReviewResDTO.MyReviewListRes;
import com.umc.umcmission.domain.review.dto.ReviewResDTO.MyReviewPreview;
import com.umc.umcmission.domain.review.entity.Review;
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
  public MyReviewListRes getMyReviews(Long userId, Long lastId, Integer lastRating, String sortType, int size) {
    if (!userRepository.existsById(userId)) {
      throw new ProjectException(UserErrorCode.USER_NOT_FOUND);
    }

    // size+1개 조회하여 다음 페이지 존재 여부 확인
    PageRequest pageRequest = PageRequest.of(0, size + 1);
    List<Review> reviews;

    if ("RATING".equalsIgnoreCase(sortType)) {
      reviews = (lastRating == null || lastId == null)
          ? reviewRepository.findByUserIdOrderByRating(userId, pageRequest)
          : reviewRepository.findByUserIdWithRatingCursor(userId, lastRating, lastId, pageRequest);
    } else {
      reviews = (lastId == null)
          ? reviewRepository.findByUserIdOrderById(userId, pageRequest)
          : reviewRepository.findByUserIdWithIdCursor(userId, lastId, pageRequest);
    }

    boolean hasNext = reviews.size() > size;
    List<Review> pageItems = hasNext ? reviews.subList(0, size) : reviews;

    List<MyReviewPreview> previewList = pageItems.stream()
        .map(this::toMyReviewPreview)
        .toList();

    Review lastItem = pageItems.isEmpty() ? null : pageItems.get(pageItems.size() - 1);
    return MyReviewListRes.builder()
        .reviewList(previewList)
        .nextLastId(lastItem != null ? lastItem.getId() : null)
        .nextLastRating(lastItem != null ? lastItem.getRating() : null)
        .hasNext(hasNext)
        .build();
  }

  private MyReviewPreview toMyReviewPreview(Review review) {
    return MyReviewPreview.builder()
        .reviewId(review.getId())
        .storeName(review.getStore().getStoreName())
        .rating(review.getRating())
        .content(review.getContent())
        .createdAt(review.getCreatedAt())
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