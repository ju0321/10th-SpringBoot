package com.umc.umcmission.domain.user.exception.code;

import com.umc.umcmission.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserSuccessCode implements BaseSuccessCode {

  USER_FOUND(HttpStatus.OK,    "USER200_1", "유저 정보를 성공적으로 조회했습니다."),
  USER_UPDATED(HttpStatus.OK,  "USER200_2", "유저 정보가 성공적으로 수정되었습니다."),
  SIGNUP_SUCCESS(HttpStatus.CREATED, "AUTH201_1", "회원가입이 완료되었습니다."),
  LOGIN_SUCCESS( HttpStatus.OK,      "AUTH200_1", "로그인에 성공했습니다."),
  WITHDRAW_SUCCESS(HttpStatus.OK, "USER200_3", "계정이 탈퇴되었습니다."),
  ;

  private final HttpStatus status;
  private final String code;
  private final String message;
}
