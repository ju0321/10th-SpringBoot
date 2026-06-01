package com.umc.umcmission.global.security.service;

import com.umc.umcmission.domain.user.entity.User;
import com.umc.umcmission.domain.user.enums.SocialType;
import com.umc.umcmission.domain.user.exception.UserException;
import com.umc.umcmission.domain.user.exception.code.UserErrorCode;
import com.umc.umcmission.domain.user.repository.UserRepository;
import com.umc.umcmission.global.security.entity.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
    return new AuthMember(user);
  }


  public UserDetails loadUserByUidAndSocialType(
      SocialType socialType,
      String username
  )throws UsernameNotFoundException{
    //DB에서 기존 회원 정보 조회 & 인증 객체 생성
    User user = userRepository.findBySocialTypeAndSocialUid(socialType, username)
        .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

    return new AuthMember(user);
  }


}
