package com.umc.umcmission.domain.user.exception.code;

import com.umc.umcmission.global.BaseErrorCode;
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
  ;

  private final HttpStatus status;
  private final String code;
  private final String message;
}
