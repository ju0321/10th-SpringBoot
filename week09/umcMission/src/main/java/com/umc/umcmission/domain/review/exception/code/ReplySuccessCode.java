package com.umc.umcmission.domain.review.exception.code;

import com.umc.umcmission.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReplySuccessCode implements BaseSuccessCode {
  REPLY_CREATED(HttpStatus.CREATED, "REPLY201_1", "답글이 성공적으로 등록되었습니다."),
  REPLY_FOUND(HttpStatus.OK, "REPLY200_1", "답글이 성공적으로 조회되었습니다."),
  REPLY_UPDATED(HttpStatus.OK,      "REPLY200_2", "답글이 성공적으로 수정되었습니다."),
  REPLY_DELETED(HttpStatus.OK,      "REPLY200_3", "답글이 성공적으로 삭제되었습니다."),
  ;

  private final HttpStatus status;
  private final String code;
  private final String message;

}
