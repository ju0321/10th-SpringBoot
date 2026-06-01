package com.umc.umcmission.global.security.dto;

import com.umc.umcmission.domain.user.enums.SocialType;

public interface OAuthDTO {
  SocialType getSocialType();
  String getSocialUid();
  String getSocialEmail();
  String getName();

}
