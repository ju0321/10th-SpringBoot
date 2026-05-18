package com.umc.umcmission.domain.user.exception.code;

import com.umc.umcmission.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements BaseErrorCode {

  USER_NOT_FOUND(HttpStatus.NOT_FOUND,
      "USER404_1", "존재하지 않는 회원입니다."),
  EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT,
      "USER409_1", "이미 사용 중인 이메일입니다."),
  PASSWORD_MISMATCH(HttpStatus.UNAUTHORIZED,
      "USER401_1", "비밀번호가 올바르지 않습니다."),
  PASSWORDS_NOT_MATCHING(HttpStatus.BAD_REQUEST,
      "USER400_1", "비밀번호와 비밀번호 확인이 일치하지 않습니다."),
  TERM_NOT_FOUND(HttpStatus.NOT_FOUND,
      "USER404_2", "존재하지 않는 약관입니다."),
  FOOD_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND,
      "USER404_3", "존재하지 않는 음식 카테고리입니다."),
  ;

  private final HttpStatus status;
  private final String code;
  private final String message;
}
