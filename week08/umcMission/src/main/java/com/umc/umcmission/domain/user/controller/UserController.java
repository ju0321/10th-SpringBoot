package com.umc.umcmission.domain.user.controller;

import com.umc.umcmission.domain.user.dto.UserReqDTO;
import com.umc.umcmission.domain.user.dto.UserResDTO;
import com.umc.umcmission.domain.user.dto.UserResDTO.MyPageRes;
import com.umc.umcmission.domain.user.exception.code.UserSuccessCode;
import com.umc.umcmission.domain.user.service.UserService;
import com.umc.umcmission.global.apiPayload.ApiResponse;
import com.umc.umcmission.global.security.entity.AuthMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "회원 정보 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

  private final UserService userService;

  @Operation(summary = "마이페이지 조회", description = "로그인한 유저의 마이페이지 정보를 조회하는 API (Private)")
  @GetMapping("/mypage")
  public ApiResponse<MyPageRes> getMyPage(
      @AuthenticationPrincipal AuthMember authMember
  ) {
    MyPageRes result = userService.getMyPage(authMember.getUser().getId());
    return ApiResponse.onSuccess(UserSuccessCode.USER_FOUND, result);
  }

  @Operation(summary = "내 정보 수정", description = "로그인한 유저의 정보를 수정하는 API (Private)")
  @PatchMapping("/me")
  public ApiResponse<MyPageRes> updateUser(
      @AuthenticationPrincipal AuthMember authMember,
      @RequestBody @Valid UserReqDTO.UpdateUserReq request
  ) {
    MyPageRes result = userService.updateUser(authMember.getUser().getId(), request);
    return ApiResponse.onSuccess(UserSuccessCode.USER_UPDATED, result);
  }

  @Operation(summary = "회원가입", description = "이메일·비밀번호로 새 계정을 생성하는 API (Public)")
  @PostMapping("/signup")
  public ApiResponse<UserResDTO.SignupRes> signup(
      @RequestBody @Valid UserReqDTO.SignupReq request
  ) {
    UserResDTO.SignupRes result = userService.signup(request);
    return ApiResponse.onSuccess(UserSuccessCode.SIGNUP_SUCCESS, result);
  }

  @Operation(summary = "로그인", description = "이메일·비밀번호로 로그인하고 서버 세션을 생성하는 API (Public)")
  @PostMapping("/login")
  public ApiResponse<UserResDTO.LoginRes> login(
      @RequestBody @Valid UserReqDTO.LoginReq request,
      HttpServletRequest httpRequest
  ) {
    UserResDTO.LoginRes result = userService.login(request, httpRequest);
    return ApiResponse.onSuccess(UserSuccessCode.LOGIN_SUCCESS, result);
  }

  @Operation(summary = "계정 탈퇴", description = "로그인한 유저의 계정을 탈퇴하는 API (Private)")
  @DeleteMapping("/me")
  public ApiResponse<Void> withdraw(
      @AuthenticationPrincipal AuthMember authMember,
      HttpServletRequest httpRequest
  ) {
    userService.withdraw(authMember.getUser().getId());
    HttpSession session = httpRequest.getSession(false);
    if (session != null) {
      session.invalidate();
    }
    SecurityContextHolder.clearContext();
    return ApiResponse.onSuccess(UserSuccessCode.WITHDRAW_SUCCESS, null);
  }
}
