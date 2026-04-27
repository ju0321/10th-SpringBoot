package com.umc.umcmission.domain.user.controller;

import com.umc.umcmission.domain.user.dto.UserReqDTO;
import com.umc.umcmission.domain.user.dto.UserResDTO;
import com.umc.umcmission.domain.user.exception.code.UserSuccessCode;
import com.umc.umcmission.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "회원 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

  @Operation(summary = "회원가입", description = "새로운 회원을 등록하는 API")
  @PostMapping("/signup")
  public ApiResponse<UserResDTO.LoginRes> join(
      @RequestBody @Valid UserReqDTO.JoinReq request
  ) {
    return ApiResponse.onSuccess(UserSuccessCode.USER_JOIN_SUCCESS, null);
  }

  @Operation(summary = "로그인", description = "로그인 API")
  @PostMapping("/login")
  public ApiResponse<UserResDTO.LoginRes> login(
      @RequestBody @Valid UserReqDTO.LoginReq request
  ) {
    return ApiResponse.onSuccess(UserSuccessCode.USER_LOGIN_SUCCESS, null);
  }

  @Operation(summary = "로그아웃", description = "로그아웃 API")
  @PostMapping("/{userId}/logout")
  public ApiResponse<Void> logout(
      @Parameter(description = "유저 ID") @PathVariable Long userId
  ) {
    return ApiResponse.onSuccess(UserSuccessCode.LOGOUT_SUCCESS, null);
  }

  @Operation(summary = "계정 탈퇴", description = "계정을 탈퇴하는 API")
  @DeleteMapping("/{userId}")
  public ApiResponse<Void> withdraw(
      @Parameter(description = "유저 ID") @PathVariable Long userId
  ) {
    return ApiResponse.onSuccess(UserSuccessCode.WITHDRAW_SUCCESS, null);
  }

  @Operation(summary = "홈 화면 조회", description = "진행하지 않은 미션 목록과 달성 미션 수를 조회하는 API")
  @GetMapping("/{userId}/home")
  public ApiResponse<UserResDTO.Home> getHome(
      @Parameter(description = "유저 ID") @PathVariable Long userId
  ) {
    return ApiResponse.onSuccess(UserSuccessCode.HOME_FOUND, null);
  }

  @Operation(summary = "마이페이지 조회", description = "유저의 마이페이지 정보를 조회하는 API")
  @GetMapping("/{userId}/mypage")
  public ApiResponse<UserResDTO.MyPageRes> getMyPage(
      @Parameter(description = "유저 ID") @PathVariable Long userId
  ) {
    return ApiResponse.onSuccess(UserSuccessCode.USER_FOUND, null);
  }

  @Operation(summary = "내 정보 수정", description = "유저 정보를 수정하는 API")
  @PatchMapping("/{userId}")
  public ApiResponse<UserResDTO.MyPageRes> updateUser(
      @Parameter(description = "유저 ID") @PathVariable Long userId,
      @RequestBody @Valid UserReqDTO.UpdateUserReq request
  ) {
    return ApiResponse.onSuccess(UserSuccessCode.USER_UPDATED, null);
  }
}
