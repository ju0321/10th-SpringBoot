package com.umc.umcmission.domain.mission.controller;

import com.umc.umcmission.domain.mission.dto.MissionReqDTO;
import com.umc.umcmission.domain.mission.dto.MissionResDTO;
import com.umc.umcmission.domain.mission.enums.MissionStatus;
import com.umc.umcmission.domain.mission.exception.code.MissionSuccessCode;
import com.umc.umcmission.domain.mission.service.MissionService;
import com.umc.umcmission.domain.mission.service.UserMissionService;
import com.umc.umcmission.global.apiPayload.ApiResponse;
import com.umc.umcmission.global.apiPayload.page.PageResDTO;
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

@Tag(name = "UserMission", description = "유저 미션 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserMissionController {

  private final MissionService missionService;
  private final UserMissionService userMissionService;

  @Operation(summary = "미션 목록 조회", description = "수행중/완료한 미션 목록을 조회하는 API")
  @GetMapping("/{userId}/missions")
  public ApiResponse<PageResDTO.PageRes<MissionResDTO.MissionPreview>> getMyMissions(
      @Parameter(description = "유저 ID") @PathVariable Long userId,
      @Parameter(description = "미션 상태 (IN_PROGRESS, COMPLETED)") @RequestParam MissionStatus status,
      @Parameter(description = "페이지 번호") @RequestParam(defaultValue = "0") int page,
      @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "10") int size
  ) {
    return ApiResponse.onSuccess(MissionSuccessCode.MISSION_LIST_FOUND,
        userMissionService.getMyMissions(userId, status, page, size));
  }

  @Operation(summary = "진행중인 미션 목록 조회", description = "유저 ID를 쿼리 파라미터로 받아 진행중인 미션 목록을 조회하는 API (오프셋 기반 페이지네이션)")
  @PostMapping("/in-progress-missions")
  public ApiResponse<PageResDTO.PageRes<MissionResDTO.MissionPreview>> getMyInProgressMissions(
      @Parameter(description = "유저 ID") @RequestParam Long userId,
      @RequestBody @Valid MissionReqDTO.GetMyMissionsReq request
  ) {
    return ApiResponse.onSuccess(MissionSuccessCode.MISSION_LIST_FOUND,
        userMissionService.getMyInProgressMissions(userId, request));
  }

  @Operation(summary = "미션 완료 처리", description = "수행중인 미션을 완료 처리하는 API")
  @PatchMapping("/{userId}/missions/{userMissionId}/complete")
  public ApiResponse<MissionResDTO.MissionCompleteRes> completeMission(
      @Parameter(description = "유저 ID") @PathVariable Long userId,
      @Parameter(description = "유저 미션 ID") @PathVariable Long userMissionId
  ) {
    return ApiResponse.onSuccess(MissionSuccessCode.MISSION_COMPLETED, null);
  }

  @Operation(summary = "홈 화면 조회", description = "진행하지 않은 미션 목록과 달성 미션 수를 조회하는 API")
  @GetMapping("/{userId}/home")
  public ApiResponse<MissionResDTO.HomeRes> getHome(
      @Parameter(description = "유저 ID") @PathVariable Long userId,
      @Parameter(description = "지역 ID") @RequestParam Long regionId,
      @Parameter(description = "페이지 번호") @RequestParam(defaultValue = "0") int page,
      @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "10") int size
  ) {
    return ApiResponse.onSuccess(MissionSuccessCode.MISSION_LIST_FOUND,
        missionService.getHome(userId, regionId, page, size));
  }

}
