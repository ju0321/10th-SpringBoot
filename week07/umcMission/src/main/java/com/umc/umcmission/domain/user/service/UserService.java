package com.umc.umcmission.domain.user.service;

import com.umc.umcmission.domain.user.dto.UserResDTO.MyPageRes;
import com.umc.umcmission.domain.user.entity.User;
import com.umc.umcmission.domain.user.exception.code.UserErrorCode;
import com.umc.umcmission.domain.user.repository.UserRepository;
import com.umc.umcmission.global.apiPayload.exception.ProjectException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  @Transactional
  public MyPageRes getMyPage(Long userId) {
    //1. 유저 있는지 찾기
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ProjectException(UserErrorCode.USER_NOT_FOUND));
    //2. response 응답 빌드해서 리턴하기
    return toGetMyPage(user);
  }


  private MyPageRes toGetMyPage(User user) {
    return MyPageRes.builder()
        .nickName(user.getNickname())
        .email(user.getEmail())
        .phoneNumber(user.getPhoneNumber())
        .point(user.getPoint())
        .build();
  }


}