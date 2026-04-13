package com.umc.umcmission.global;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProjectException extends RuntimeException {
  private final BaseErrorCode errorCode;
}
