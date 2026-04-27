package com.umc.umcmission.domain.mission.controller;

import com.umc.umcmission.domain.mission.dto.MissionResDTO;
import com.umc.umcmission.domain.mission.enums.MissionStatus;
import com.umc.umcmission.domain.mission.exception.code.MissionSuccessCode;
import com.umc.umcmission.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "UserMission", description = "유저 미션 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserMissionController {

  @Operation(summary = "미션 목록 조회", description = "수행중/완료한 미션 목록을 조회하는 API")
  @GetMapping("/{userId}/missions")
  public ApiResponse<List<MissionResDTO.MissionPreview>> getMyMissions(
      @Parameter(description = "유저 ID") @PathVariable Long userId,
      @Parameter(description = "미션 상태 (IN_PROGRESS, COMPLETED)") @RequestParam MissionStatus status
  ) {
    return ApiResponse.onSuccess(MissionSuccessCode.MISSION_LIST_FOUND, null);
  }

  @Operation(summary = "미션 완료 처리", description = "수행중인 미션을 완료 처리하는 API")
  @PatchMapping("/{userId}/missions/{userMissionId}/complete")
  public ApiResponse<MissionResDTO.MissionCompleteRes> completeMission(
      @Parameter(description = "유저 ID") @PathVariable Long userId,
      @Parameter(description = "유저 미션 ID") @PathVariable Long userMissionId
  ) {
    return ApiResponse.onSuccess(MissionSuccessCode.MISSION_COMPLETED, null);
  }
}
