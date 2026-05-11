package com.umc.umcmission.domain.review.controller;

import com.umc.umcmission.domain.review.dto.ReviewResDTO;
import com.umc.umcmission.domain.review.exception.code.ReviewSuccessCode;
import com.umc.umcmission.domain.review.service.ReviewService;
import com.umc.umcmission.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "UserReview", description = "내 리뷰 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserReviewController {

  private final ReviewService reviewService;

  @Operation(
      summary = "내가 생성한 리뷰 목록 조회",
      description = "커서 기반 페이지네이션으로 내 리뷰를 조회합니다. "
          + "query=id(ID 순) 또는 query=rating(별점 내림차순). "
          + "첫 페이지는 cursor=-1로 요청, 이후 응답의 nextCursor를 cursor로 사용하세요."
  )
  @GetMapping("/{userId}/reviews")
  public ApiResponse<ReviewResDTO.Pagination<ReviewResDTO.MyReviewPreview>> getMyReviews(
      @Parameter(description = "유저 ID") @PathVariable Long userId,
      @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "10") Integer pageSize,
      @Parameter(description = "커서 (첫 페이지는 -1)") @RequestParam(defaultValue = "-1") String cursor,
      @Parameter(description = "정렬 기준: id 또는 rating") @RequestParam(defaultValue = "id") String query
  ) {
    return ApiResponse.onSuccess(ReviewSuccessCode.MY_REVIEW_LIST_FOUND,
        reviewService.getMyReviews(userId, pageSize, cursor, query));
  }
}
