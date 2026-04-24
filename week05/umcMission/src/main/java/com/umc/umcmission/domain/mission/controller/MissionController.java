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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Mission", description = "미션 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MissionController {

  @Operation(summary = "가게 미션 추가", description = "특정 가게에 미션을 등록합니다.")
  @PostMapping("/stores/{storeId}/missions")
  public ApiResponse<MissionResDTO.AddMissionRes> addMission(
      @Parameter(description = "가게 ID") @PathVariable Long storeId,
      @RequestBody @Valid MissionReqDTO.AddMissionReq request
  ) {

    return ApiResponse.onSuccess(MissionSuccessCode.MISSION_CREATED, null);
  }

  @Operation(summary = "미션 도전하기", description = "특정 미션에 도전을 시작합니다.")
  @PostMapping("/missions/{missionId}/challenge")
  public ApiResponse<MissionResDTO.ChallengeMissionRes> challengeMission(
      @Parameter(description = "미션 ID") @PathVariable Long missionId,
      @RequestBody @Valid MissionReqDTO.ChallengeMissionReq request
  ) {

    return ApiResponse.onSuccess(MissionSuccessCode.MISSION_CHALLENGED, null);
  }

  @Operation(summary = "진행 중인 미션 목록 조회", description = "특정 유저의 진행 중인 미션 목록을 페이지 단위로 조회합니다.")
  @GetMapping("/users/{userId}/missions")
  public ApiResponse<MissionResDTO.MissionPreviewListRes> getMyMissions(
      @Parameter(description = "유저 ID") @PathVariable Long userId,
      @Parameter(description = "페이지 번호 (0부터 시작)") @RequestParam(defaultValue = "0") int page,
      @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "10") int size
  ) {

    return ApiResponse.onSuccess(MissionSuccessCode.MISSION_LIST_FOUND, null);
  }

  @Operation(summary = "미션 완료 처리", description = "진행 중인 미션을 완료 상태로 변경합니다.")
  @PatchMapping("/users/{userId}/missions/{userMissionId}")
  public ApiResponse<MissionResDTO.UpdateMissionStatusRes> completeMission(
      @Parameter(description = "유저 ID") @PathVariable Long userId,
      @Parameter(description = "유저 미션 ID") @PathVariable Long userMissionId,
      @RequestBody @Valid MissionReqDTO.UpdateMissionStatusReq request
  ) {

    return ApiResponse.onSuccess(MissionSuccessCode.MISSION_COMPLETED, null);
  }
}
