package com.umc.umcmission.domain.review.dto;

import java.time.LocalDateTime;
import lombok.Builder;

public class ReplyResDTO {

  @Builder
  public record ReplyRes(
      Long replyId,
      String content,
      LocalDateTime createdAt
  ){}

}
