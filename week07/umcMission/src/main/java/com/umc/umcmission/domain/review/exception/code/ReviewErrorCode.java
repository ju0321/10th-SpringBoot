package com.umc.umcmission.domain.review.exception.code;

import com.umc.umcmission.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {

  REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND,
      "REVIEW404_1", "존재하지 않는 리뷰입니다."),
  QUERY_NOT_VALID(HttpStatus.BAD_REQUEST,
      "REVIEW400_1", "유효하지 않은 정렬 기준입니다."),
  ;

  private final HttpStatus status;
  private final String code;
  private final String message;
}
