package com.umc.umcmission.domain.user.dto;

import com.umc.umcmission.domain.user.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;

public class UserReqDTO {

  @Builder
  public record UpdateUserReq(
      String nickname,
      String address,
      @NotBlank(message = "휴대폰번호는 필수입니다.") String phoneNumber
  ) {}

  public record SignupReq(
      @NotBlank(message = "이름은 필수입니다.") String name,
      String nickname,
      @Email(message = "올바른 이메일 형식이 아닙니다.") @NotBlank(message = "이메일은 필수입니다.") String email,
      @NotBlank(message = "비밀번호는 필수입니다.") String password,
      @NotBlank(message = "주소는 필수입니다.") String address,
      String phoneNumber,
      LocalDate birthDate,
      @NotNull(message = "성별은 필수입니다.") Gender gender,
      List<Long> termIds,
      List<Long> foodIds
  ) {}

  public record LoginReq(
      @Email(message = "올바른 이메일 형식이 아닙니다.") @NotBlank(message = "이메일은 필수입니다.") String email,
      @NotBlank(message = "비밀번호는 필수입니다.") String password
  ) {}
}
