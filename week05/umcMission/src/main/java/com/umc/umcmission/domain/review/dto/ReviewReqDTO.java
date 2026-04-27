package com.umc.umcmission.domain.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public class ReviewReqDTO {

  @Builder
  public record AddReviewReq(
      @NotNull(message = "유저 ID는 필수입니다.") Long userId,
      @NotNull(message = "별점은 필수입니다.") @Min(value = 1, message = "별점은 최소 1점입니다.") @Max(value = 5, message = "별점은 최대 5점입니다.") Integer rating,
      String content,
      String imageUrl
  ) {}
}