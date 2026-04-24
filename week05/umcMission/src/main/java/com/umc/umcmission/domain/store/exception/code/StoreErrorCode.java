package com.umc.umcmission.domain.store.exception.code;

import com.umc.umcmission.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StoreErrorCode implements BaseErrorCode {

  STORE_NOT_FOUND(HttpStatus.NOT_FOUND,
      "STORE404_1", "존재하지 않는 가게입니다."),
  REGION_NOT_FOUND(HttpStatus.NOT_FOUND,
      "STORE404_2", "존재하지 않는 지역입니다."),
  ;

  private final HttpStatus status;
  private final String code;
  private final String message;
}
