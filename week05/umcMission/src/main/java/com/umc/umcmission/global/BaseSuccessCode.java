package com.umc.umcmission.global;

import org.springframework.http.HttpStatus;

public interface BaseSuccessCode {
  HttpStatus getStatus();
  String getCode();
  String getMessage();
}
