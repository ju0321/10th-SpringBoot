package com.umc.umcmission.domain.review.exception;

import com.umc.umcmission.domain.review.exception.code.ReviewErrorCode;
import com.umc.umcmission.global.ProjectException;

public class ReviewException extends ProjectException {

  public ReviewException(ReviewErrorCode errorCode) {
    super(errorCode);
  }
}
