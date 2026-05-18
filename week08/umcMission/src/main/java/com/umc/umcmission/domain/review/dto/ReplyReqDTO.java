package com.umc.umcmission.domain.review.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public class ReplyReqDTO {

  @Builder
  public record CreateReplyReq(
      @NotNull(message = "유저 ID는 필수입니다.") Long userId,
      @NotBlank(message = "답글 내용은 필수입니다.")  String content
  ){}

  @Builder
  public record UpdateReplyReq(
      @NotBlank(message = "답글 내용은 필수입니다.") String content
  ) {}

}
