package com.umc.umcmission.domain.user.exception.code;

import com.umc.umcmission.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements BaseErrorCode {

  USER_NOT_FOUND(HttpStatus.NOT_FOUND,    "USER404_1", "존재하지 않는 회원입니다."),
  TERM_NOT_FOUND(HttpStatus.NOT_FOUND,    "USER404_2", "존재하지 않는 약관입니다."),
  FOOD_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "USER404_3", "존재하지 않는 음식 카테고리입니다."),
  EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT,      "AUTH409_1", "이미 사용 중인 이메일입니다."),
  INVALID_CREDENTIALS( HttpStatus.UNAUTHORIZED,  "AUTH401_1", "이메일 또는 비밀번호가 올바르지 않습니다."),
  REQUIRED_TERM_NOT_AGREED(HttpStatus.BAD_REQUEST, "USER400_1", "필수 약관에 모두 동의해야 합니다."),
  NOT_SUPPORT_SOCIAL_PROVIDER(HttpStatus.BAD_REQUEST, "USER400_2", "지원하지 않는 소셜 로그인 제공자입니다."),
  ;

  private final HttpStatus status;
  private final String code;
  private final String message;
}
