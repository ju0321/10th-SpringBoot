package com.umc.umcmission.domain.review.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

public class ReviewResDTO {

  @Builder
  public record AddReviewRes(
      Long reviewId,
      Integer rating,
      String content,
      LocalDateTime createdAt
  ) {}

  @Builder
  public record ReviewPreview(
      Long reviewId,
      String nickname,
      Integer rating,
      String content,
      String imageUrl,
      LocalDateTime createdAt
  ) {}

  @Builder
  public record ReviewPreviewListRes(
      List<ReviewPreview> reviewList
  ) {}
}
