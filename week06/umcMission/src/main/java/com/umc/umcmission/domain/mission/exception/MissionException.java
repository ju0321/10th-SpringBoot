package com.umc.umcmission.domain.mission.exception;

import com.umc.umcmission.domain.mission.exception.code.MissionErrorCode;
import com.umc.umcmission.global.apiPayload.exception.ProjectException;

public class MissionException extends ProjectException {

  public MissionException(MissionErrorCode errorCode) {
    super(errorCode);
  }
}
