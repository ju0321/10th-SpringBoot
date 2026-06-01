package com.umc.umcmission.global.security.entity;

import com.umc.umcmission.domain.user.entity.User;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@RequiredArgsConstructor
public class OAuthMember implements OAuth2User {

  @Getter
  private final User user;
  private final Map<String, Object> attributes;

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }

  @Override
  public String getName() {
    return user.getSocialUid();
  }
}
