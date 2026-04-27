package com.umc.umcmission.domain.user.exception.code;

import com.umc.umcmission.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserSuccessCode implements BaseSuccessCode {

  USER_JOIN_SUCCESS(HttpStatus.CREATED, "USER201_1", "회원가입이 완료되었습니다."),
  USER_LOGIN_SUCCESS(HttpStatus.OK,     "USER200_1", "로그인이 완료되었습니다."),
  HOME_FOUND(HttpStatus.OK,             "USER200_2", "홈 화면을 성공적으로 조회했습니다."),
  USER_FOUND(HttpStatus.OK,             "USER200_3", "유저 정보를 성공적으로 조회했습니다."),
  USER_UPDATED(HttpStatus.OK,           "USER200_4", "유저 정보가 성공적으로 수정되었습니다."),
  LOGOUT_SUCCESS(HttpStatus.OK,         "USER200_5", "로그아웃이 완료되었습니다."),
  WITHDRAW_SUCCESS(HttpStatus.OK,       "USER200_6", "계정이 탈퇴되었습니다."),
  ;

  private final HttpStatus status;
  private final String code;
  private final String message;
}
