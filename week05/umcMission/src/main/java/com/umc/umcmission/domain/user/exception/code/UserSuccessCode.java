package com.umc.umcmission.domain.user.exception.code;

import com.umc.umcmission.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserSuccessCode implements BaseSuccessCode {

  USER_JOIN_SUCCESS(HttpStatus.CREATED,
      "USER201_1", "회원가입이 완료되었습니다."),
  ;

  private final HttpStatus status;
  private final String code;
  private final String message;
}
