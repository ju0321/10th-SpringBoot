package com.umc.umcmission.domain.mission.controller;

import com.umc.umcmission.domain.mission.dto.MissionReqDTO;
import com.umc.umcmission.domain.mission.dto.MissionResDTO;
import com.umc.umcmission.domain.mission.exception.code.MissionSuccessCode;
import com.umc.umcmission.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Mission", description = "미션 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/missions")
public class MissionController {

  @Operation(summary = "미션 도전", description = "특정 미션에 도전을 시작하는 API")
  @PostMapping("/{missionId}/challenge")
  public ApiResponse<MissionResDTO.ChallengeMissionRes> challengeMission(
      @Parameter(description = "미션 ID") @PathVariable Long missionId,
      @RequestBody @Valid MissionReqDTO.ChallengeMissionReq request
  ) {


    return ApiResponse.onSuccess(MissionSuccessCode.MISSION_CHALLENGED, null);
  }
}
