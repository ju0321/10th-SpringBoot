package com.umc.umcmission.domain.user.dto;

import com.umc.umcmission.domain.user.enums.FoodCategory;
import com.umc.umcmission.domain.user.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;

public class UserReqDTO {

  @Builder
  public record JoinReq(
      @NotBlank(message = "이름은 필수입니다.") String name,
      String nickname,
      @NotBlank(message = "이메일은 필수입니다.") @Email(message = "올바른 이메일 형식이 아닙니다.") String email,
      @NotBlank(message = "비밀번호는 필수입니다.") @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.") String password,
      @NotBlank(message = "비밀번호 확인은 필수입니다.") String passwordConfirm,
      @NotBlank(message = "주소는 필수입니다.") String address,
      @Past(message = "생년월일은 과거 날짜여야 합니다.") LocalDate birthDate,
      @NotNull(message = "성별은 필수입니다.") Gender gender,
      List<FoodCategory> foodCategories
  ) {}

  @Builder
  public record LoginReq(
      @NotBlank(message = "이메일은 필수입니다.") @Email(message = "올바른 이메일 형식이 아닙니다.") String email,
      @NotBlank(message = "비밀번호는 필수입니다.") @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.") String password
  ) {}

  @Builder
  public record UpdateUserReq(
      String nickname,
      String address,
      @NotBlank(message = "휴대폰번호는 필수입니다.") String phoneNumber
  ) {}

}
