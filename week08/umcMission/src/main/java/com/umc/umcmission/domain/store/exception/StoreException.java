package com.umc.umcmission.domain.store.exception;

import com.umc.umcmission.domain.store.exception.code.StoreErrorCode;
import com.umc.umcmission.global.apiPayload.exception.ProjectException;

public class StoreException extends ProjectException {

  public StoreException(StoreErrorCode errorCode) {
    super(errorCode);
  }
}
