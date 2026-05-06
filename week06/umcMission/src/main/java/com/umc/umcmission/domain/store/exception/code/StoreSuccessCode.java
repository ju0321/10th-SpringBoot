package com.umc.umcmission.domain.store.exception.code;

import com.umc.umcmission.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StoreSuccessCode implements BaseSuccessCode {

  STORE_CREATED(HttpStatus.CREATED, "STORE201_1", "가게가 성공적으로 등록되었습니다."),
  STORE_LIST_FOUND(HttpStatus.OK,   "STORE200_1", "가게 목록을 성공적으로 조회했습니다."),
  STORE_FOUND(HttpStatus.OK,        "STORE200_2", "가게 정보를 성공적으로 조회했습니다."),
  MISSION_CREATED(HttpStatus.CREATED, "STORE201_2", "미션이 성공적으로 등록되었습니다."),
  ;

  private final HttpStatus status;
  private final String code;
  private final String message;
}
