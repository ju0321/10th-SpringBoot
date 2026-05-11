package com.umc.umcmission.domain.review.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

public class ReviewResDTO {

  @Builder
  public record CreateReviewRes(
      Long reviewId,
      Integer rating,
      String content,
      LocalDateTime createdAt
  ) {}

  @Builder
  public record GetReviewRes(
      Long reviewId,
      String nickname,
      Integer rating,
      String content,
      String imageUrl,
      LocalDateTime createdAt
  ) {}

  @Builder
  public record GetReviewListRes(
      List<GetReviewRes> reviewList
  ) {}

  // 커서 기반 페이지네이션용 (사진 제외)
  @Builder
  public record MyReviewPreview(
      Long reviewId,
      String storeName,
      Integer rating,
      String content,
      LocalDateTime createdAt
  ) {}

  @Builder
  public record MyReviewListRes(
      List<MyReviewPreview> reviewList,
      Long nextLastId,
      Integer nextLastRating,
      boolean hasNext
  ) {}
}
