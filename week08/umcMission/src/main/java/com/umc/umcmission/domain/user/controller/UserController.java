package com.umc.umcmission.domain.user.controller;

import com.umc.umcmission.domain.user.dto.UserReqDTO;
import com.umc.umcmission.domain.user.dto.UserResDTO;
import com.umc.umcmission.domain.user.dto.UserResDTO.MyPageRes;
import com.umc.umcmission.domain.user.exception.code.UserSuccessCode;
import com.umc.umcmission.domain.user.service.UserService;
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

  private final UserService userService;

  @Operation(summary = "회원가입", description = "새로운 회원을 등록하는 API (Public)")
  @PostMapping("/signup")
  public ApiResponse<UserResDTO.JoinRes> join(
      @RequestBody @Valid UserReqDTO.JoinReq request
  ) {
    return ApiResponse.onSuccess(UserSuccessCode.USER_JOIN_SUCCESS, userService.join(request));
  }

  @Operation(summary = "로그인", description = "로그인 후 JWT 토큰을 반환하는 API (Public)")
  @PostMapping("/login")
  public ApiResponse<UserResDTO.LoginRes> login(
      @RequestBody @Valid UserReqDTO.LoginReq request
  ) {
    return ApiResponse.onSuccess(UserSuccessCode.USER_LOGIN_SUCCESS, userService.login(request));
  }

  @Operation(summary = "로그아웃", description = "로그아웃 API (Private)")
  @PostMapping("/{userId}/logout")
  public ApiResponse<Void> logout(
      @Parameter(description = "유저 ID") @PathVariable Long userId
  ) {
    return ApiResponse.onSuccess(UserSuccessCode.LOGOUT_SUCCESS, null);
  }

  @Operation(summary = "계정 탈퇴", description = "계정을 탈퇴하는 API (Private)")
  @DeleteMapping("/{userId}")
  public ApiResponse<Void> withdraw(
      @Parameter(description = "유저 ID") @PathVariable Long userId
  ) {
    return ApiResponse.onSuccess(UserSuccessCode.WITHDRAW_SUCCESS, null);
  }

  @Operation(summary = "마이페이지 조회", description = "유저의 마이페이지 정보를 조회하는 API (Private)")
  @GetMapping("/{userId}/mypage")
  public ApiResponse<UserResDTO.MyPageRes> getMyPage(
      @Parameter(description = "유저 ID") @PathVariable Long userId
  ) {
    MyPageRes result = userService.getMyPage(userId);
    return ApiResponse.onSuccess(UserSuccessCode.USER_FOUND, result);
  }

  @Operation(summary = "내 정보 수정", description = "유저 정보를 수정하는 API (Private)")
  @PatchMapping("/{userId}")
  public ApiResponse<UserResDTO.MyPageRes> updateUser(
      @Parameter(description = "유저 ID") @PathVariable Long userId,
      @RequestBody @Valid UserReqDTO.UpdateUserReq request
  ) {
    return ApiResponse.onSuccess(UserSuccessCode.USER_UPDATED, null);
  }
}
