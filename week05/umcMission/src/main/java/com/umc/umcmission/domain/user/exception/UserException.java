package com.umc.umcmission.domain.user.exception;

import com.umc.umcmission.domain.user.exception.code.UserErrorCode;
import com.umc.umcmission.global.ProjectException;

public class UserException extends ProjectException {

  public UserException(UserErrorCode errorCode) {
    super(errorCode);
  }
}
