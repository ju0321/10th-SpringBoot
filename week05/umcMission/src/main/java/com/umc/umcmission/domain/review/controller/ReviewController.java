package com.umc.umcmission.domain.review.controller;

import com.umc.umcmission.domain.review.dto.ReviewReqDTO;
import com.umc.umcmission.domain.review.dto.ReviewResDTO;
import com.umc.umcmission.domain.review.exception.code.ReviewSuccessCode;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Review", description = "리뷰 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores/{storeId}")
public class ReviewController {

  @Operation(summary = "리뷰 작성", description = "특정 가게에 리뷰를 작성합니다.")
  @PostMapping("/reviews")
  public ApiResponse<ReviewResDTO.AddReviewRes> addReview(
      @Parameter(description = "가게 ID") @PathVariable Long storeId,
      @RequestBody @Valid ReviewReqDTO.AddReviewReq request
  ) {

    return ApiResponse.onSuccess(ReviewSuccessCode.REVIEW_CREATED, null);
  }

  @Operation(summary = "가게 리뷰 목록 조회", description = "특정 가게의 리뷰 목록을 페이지 단위로 조회합니다.")
  @GetMapping("/reviews")
  public ApiResponse<ReviewResDTO.ReviewPreviewListRes> getReviews(
      @Parameter(description = "가게 ID") @PathVariable Long storeId,
      @Parameter(description = "페이지 번호 (0부터 시작)") @RequestParam(defaultValue = "0") int page,
      @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "10") int size
  ) {
    //
    return ApiResponse.onSuccess(ReviewSuccessCode.REVIEW_LIST_FOUND, null);
  }
}