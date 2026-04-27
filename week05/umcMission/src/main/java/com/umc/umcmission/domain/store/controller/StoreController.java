package com.umc.umcmission.domain.store.controller;

import com.umc.umcmission.domain.mission.dto.MissionReqDTO;
import com.umc.umcmission.domain.mission.dto.MissionResDTO;
import com.umc.umcmission.domain.store.dto.StoreReqDTO;
import com.umc.umcmission.domain.store.dto.StoreResDTO;
import com.umc.umcmission.domain.store.dto.StoreResDTO.StoreListRes;
import com.umc.umcmission.domain.store.exception.code.StoreSuccessCode;
import com.umc.umcmission.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Store", description = "가게 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreController {

  @Operation(summary = "가게 목록 조회", description = "지도 화면에서 근처 가게 목록을 조회하는 API")
  @GetMapping
  public ApiResponse<List<StoreListRes>> getStores(
      @Parameter(description = "지역 ID") @RequestParam Long regionId
  ) {
    return ApiResponse.onSuccess(StoreSuccessCode.STORE_LIST_FOUND, null);
  }

  @Operation(summary = "가게 상세 조회", description = "가게 상세 정보와 리뷰 목록을 조회하는 API")
  @GetMapping("/{storeId}")
  public ApiResponse<StoreResDTO.StoreInfoRes> getStore(
      @Parameter(description = "가게 ID") @PathVariable Long storeId
  ) {
    return ApiResponse.onSuccess(StoreSuccessCode.STORE_FOUND, null);
  }

}
