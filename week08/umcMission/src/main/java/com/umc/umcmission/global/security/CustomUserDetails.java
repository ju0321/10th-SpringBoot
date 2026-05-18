package com.umc.umcmission.global.security;

import com.umc.umcmission.domain.user.entity.User;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

  // User 엔티티를 Spring Security에서 사용할 수 있도록 함
  private final User user;

  // 권한 목록 반환: Spring Security 권한은 "ROLE_USER", "ROLE_ADMIN" 형태
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  // 사용자 식별값(고유값) 반환: email을 username(UserDetails의 사용자 식별 메서드 이름)처럼 사용
  @Override
  public String getUsername() {
    return user.getEmail();
  }
}