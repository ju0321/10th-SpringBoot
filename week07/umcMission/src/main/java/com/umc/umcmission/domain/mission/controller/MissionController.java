package com.umc.umcmission.domain.mission.controller;

import com.umc.umcmission.domain.mission.dto.MissionReqDTO;
import com.umc.umcmission.domain.mission.dto.MissionResDTO;
import com.umc.umcmission.domain.mission.exception.code.MissionSuccessCode;
import com.umc.umcmission.domain.mission.service.MissionService;
import com.umc.umcmission.global.apiPayload.ApiResponse;
import com.umc.umcmission.global.apiPayload.code.BaseSuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
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

  private final MissionService missionService;

  @Operation(summary = "미션 도전", description = "특정 미션에 도전을 시작하는 API")
  @PostMapping("/missions/{missionId}/challenge")
  public ApiResponse<MissionResDTO.ChallengeMissionRes> challengeMission(
      @Parameter(description = "미션 ID") @PathVariable Long missionId,
      @RequestBody @Valid MissionReqDTO.ChallengeMissionReq request
  ) {

    return ApiResponse.onSuccess(MissionSuccessCode.MISSION_CHALLENGED, null);
  }

  @Operation(summary = "가게 미션 생성", description = "가게 미션을 생성하는 API")
  @PostMapping("/v1/stores/{storeId}/missions")
  public ApiResponse<MissionResDTO.CreateMissionRes> createMission(
      @PathVariable Long storeId,
      @RequestBody @Valid MissionReqDTO.CreateMissionReq dto
  ){
    BaseSuccessCode code = MissionSuccessCode.MISSION_CREATED;
    return ApiResponse.onSuccess(code, missionService.createMission(storeId, dto));
  }

  @Operation(summary = "가게 미션 조회", description = "가게 미션을 조회하는 APi")
  @GetMapping("/v1/stores/{storeId}/missions")
  public ApiResponse<Page<MissionResDTO.GetMission>> getMissions(
      @PathVariable Long storeId,
      @RequestParam Integer pageSize,
      @RequestParam Integer pageNum,
      @RequestParam(required = false) String sort
  ){
    BaseSuccessCode code = MissionSuccessCode.MISSION_FOUND;
    return ApiResponse.onSuccess(code, missionService.getMissions(storeId, pageSize, pageNum, sort));
  }

  @Operation(summary = "가게 미션 커서 조회", description = "커서 기반으로 가게 미션을 조회하는 API")
  @GetMapping("/v2/stores/{storeId}/missions")
  public ApiResponse<MissionResDTO.Pagination<MissionResDTO.GetMission>> getMissions2(
      @PathVariable Long storeId,
      @RequestParam Integer pageSize,
      @RequestParam String cursor,
      @RequestParam String query
  ){
    BaseSuccessCode code = MissionSuccessCode.MISSION_FOUND;
    return ApiResponse.onSuccess(code, missionService.getMissions2(storeId, pageSize, cursor, query));
  }
}
