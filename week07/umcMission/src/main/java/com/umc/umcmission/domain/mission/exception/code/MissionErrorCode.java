package com.umc.umcmission.domain.mission.exception.code;

import com.umc.umcmission.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MissionErrorCode implements BaseErrorCode {

  MISSION_NOT_FOUND(HttpStatus.NOT_FOUND,
      "MISSION404_1", "존재하지 않는 미션입니다."),
  MISSION_ALREADY_CHALLENGED(HttpStatus.CONFLICT,
      "MISSION409_1", "이미 도전 중인 미션입니다."),
  USER_MISSION_NOT_FOUND(HttpStatus.NOT_FOUND,
      "MISSION404_2", "진행 중인 미션 기록을 찾을 수 없습니다."),
  ;

  private final HttpStatus status;
  private final String code;
  private final String message;
}
