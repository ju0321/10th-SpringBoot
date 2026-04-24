package com.umc.umcmission.domain.user.controller;

import com.umc.umcmission.domain.user.dto.UserReqDTO;
import com.umc.umcmission.domain.user.dto.UserResDTO;
import com.umc.umcmission.domain.user.exception.code.UserSuccessCode;
import com.umc.umcmission.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "회원 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

  @Operation(summary = "회원가입", description = "새로운 회원을 등록합니다.")
  @PostMapping
  public ApiResponse<UserResDTO.JoinRes> join(
      @RequestBody @Valid UserReqDTO.JoinReq request
  ) {

    return ApiResponse.onSuccess(UserSuccessCode.USER_JOIN_SUCCESS, null);
  }
}
