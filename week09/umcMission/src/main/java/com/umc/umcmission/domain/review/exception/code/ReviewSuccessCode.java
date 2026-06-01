package com.umc.umcmission.domain.review.exception.code;

import com.umc.umcmission.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewSuccessCode implements BaseSuccessCode {

  REVIEW_CREATED(HttpStatus.CREATED,
      "REVIEW201_1", "리뷰가 성공적으로 등록되었습니다."),
  REVIEW_FOUND(HttpStatus.OK,
      "REVIEW200_1", "리뷰를 성공적으로 조회했습니다."),
  REVIEW_LIST_FOUND(HttpStatus.OK,
      "REVIEW200_2", "리뷰 목록을 성공적으로 조회했습니다."),
  MY_REVIEW_LIST_FOUND(HttpStatus.OK,
      "REVIEW200_3", "내 리뷰 목록을 성공적으로 조회했습니다."),
  ;

  private final HttpStatus status;
  private final String code;
  private final String message;
}
