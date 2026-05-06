package com.umc.umcmission.domain.user.dto;

import lombok.Builder;

public class UserResDTO {

  @Builder
  public record LoginRes(
      Long userId,
      String name
  ) {}

  @Builder
  public record MyPageRes(
      String nickName,
      String email,
      String phoneNumber,
      Integer point
  ) {}
}
