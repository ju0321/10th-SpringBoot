package com.umc.umcmission.domain.review.controller;

import com.umc.umcmission.domain.review.dto.ReviewReqDTO;
import com.umc.umcmission.domain.review.dto.ReviewResDTO;
import com.umc.umcmission.domain.review.exception.code.ReviewSuccessCode;
import com.umc.umcmission.domain.review.service.ReviewService;
import com.umc.umcmission.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Review", description = "리뷰 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores/{storeId}/reviews")
public class ReviewController {

  private final ReviewService reviewService;

  @Operation(summary = "리뷰 작성", description = "특정 가게에 리뷰를 작성합니다.")
  @PostMapping("")
  public ApiResponse<ReviewResDTO.CreateReviewRes> addReview(
      @Parameter(description = "가게 ID") @PathVariable Long storeId,
      @RequestBody @Valid ReviewReqDTO.CreateReviewReq request
  ) {
    return ApiResponse.onSuccess(ReviewSuccessCode.REVIEW_CREATED, reviewService.createReview(storeId, request));
  }

  @Operation(summary = "가게 리뷰 단건 조회", description = "특정 가게의 특정 리뷰를 조회합니다.")
  @GetMapping("/{reviewId}")
  public ApiResponse<ReviewResDTO.GetReviewRes> getReview(
      @Parameter(description = "가게 ID") @PathVariable Long storeId,
      @Parameter(description = "리뷰 ID") @PathVariable Long reviewId
  ){
    return ApiResponse.onSuccess(ReviewSuccessCode.REVIEW_FOUND, reviewService.getReview(storeId, reviewId));
  }

  @Operation(summary = "가게 리뷰 목록 조회", description = "특정 가게의 리뷰 목록을 조회합니다.")
  @GetMapping("")
  public ApiResponse<ReviewResDTO.GetReviewListRes> getReviews(
      @Parameter(description = "가게 ID") @PathVariable Long storeId
  ) {
    return ApiResponse.onSuccess(ReviewSuccessCode.REVIEW_LIST_FOUND, reviewService.getReviews(storeId));
  }
}