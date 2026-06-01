package com.umc.umcmission.global.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.umcmission.domain.user.converter.UserConverter;
import com.umc.umcmission.domain.user.dto.UserResDTO;
import com.umc.umcmission.domain.user.exception.code.UserSuccessCode;
import com.umc.umcmission.global.apiPayload.ApiResponse;
import com.umc.umcmission.global.security.entity.AuthMember;
import com.umc.umcmission.global.security.entity.OAuthMember;
import com.umc.umcmission.global.security.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@RequiredArgsConstructor
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

  private final JwtUtil jwtUtil;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication
  ) throws IOException, ServletException {

    // 사전 작업: Response 매핑할 ObjectMapper 선언
    ObjectMapper objectMapper = new ObjectMapper();
    UserSuccessCode code = UserSuccessCode.LOGIN_SUCCESS;
    // Content-Type, Status 설정
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(code.getStatus().value());

    //인증 객체 컨테이너에서 OAuth 인증 객체 가져오기
    OAuthMember oAuthMember = (OAuthMember) authentication.getPrincipal();
    //토큰 제작을 위해 OAuth 인증 객체에서 member 추출 -> AuthMember 제작
    String accessToken = jwtUtil.createAccessToken(new AuthMember(oAuthMember.getUser()));

    //응답 통일 객체 래핑
    ApiResponse<UserResDTO.OAuthLoginRes> responseBody = ApiResponse.onSuccess(
        code,
        UserConverter.toOAuthLoginRes(accessToken));

    //응답 출력
    objectMapper.writeValue(response.getOutputStream(), responseBody);
  }
}