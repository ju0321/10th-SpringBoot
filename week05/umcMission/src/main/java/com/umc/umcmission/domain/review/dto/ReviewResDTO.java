package com.umc.umcmission.domain.review.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReviewResDTO {

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class AddReviewRes {
    private Long reviewId;
    private Integer rating;
    private String content;
    private LocalDateTime createdAt;
  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ReviewPreview {
    private Long reviewId;
    private Integer rating;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;
  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ReviewPreviewListRes {
    private List<ReviewPreview> reviewList;
    private int listSize;
    private int totalPage;
    private long totalElements;
    private boolean isFirst;
    private boolean isLast;
  }
}
