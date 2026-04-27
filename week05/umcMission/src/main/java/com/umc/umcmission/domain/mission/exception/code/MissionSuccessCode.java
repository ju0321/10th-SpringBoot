package com.umc.umcmission.domain.mission.exception.code;

import com.umc.umcmission.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MissionSuccessCode implements BaseSuccessCode {

  MISSION_CREATED(HttpStatus.CREATED,
      "MISSION201_1", "미션이 성공적으로 등록되었습니다."),
  MISSION_CHALLENGED(HttpStatus.CREATED,
      "MISSION201_2", "미션 도전이 시작되었습니다."),
  MISSION_LIST_FOUND(HttpStatus.OK,
      "MISSION200_1", "미션 목록을 성공적으로 조회했습니다."),
  MISSION_COMPLETED(HttpStatus.OK,
      "MISSION200_2", "미션이 완료 처리되었습니다."),
  ;

  private final HttpStatus status;
  private final String code;
  private final String message;
}
