package com.umc.umcmission.domain.user.dto;

import lombok.Builder;

public class UserResDTO {

  @Builder
  public record MyPageRes(
      String nickName,
      String email,
      String phoneNumber,
      Integer point
  ) {}

  @Builder
  public record SignupRes(Long id, String email, String name) {}

  @Builder
  public record LoginRes(Long id, String email, String name, String role) {}

}
