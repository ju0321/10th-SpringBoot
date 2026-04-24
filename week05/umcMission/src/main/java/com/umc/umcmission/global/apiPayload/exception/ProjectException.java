package com.umc.umcmission.global.apiPayload.exception;

import com.umc.umcmission.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProjectException extends RuntimeException {
  private final BaseErrorCode errorCode;
}
