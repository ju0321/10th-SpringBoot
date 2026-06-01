package com.umc.umcmission.domain.review.exception.code;

import com.umc.umcmission.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReplyErrorCode implements BaseErrorCode {

  REPLY_NOT_FOUND(HttpStatus.NOT_FOUND,
      "REPLY404_1", "존재하지 않는 답글입니다."),
  ;

  private final HttpStatus status;
  private final String code;
  private final String message;
}

