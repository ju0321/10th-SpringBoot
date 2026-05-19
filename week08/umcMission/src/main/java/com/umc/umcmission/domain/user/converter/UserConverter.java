package com.umc.umcmission.domain.user.converter;

import com.umc.umcmission.domain.user.dto.UserReqDTO;
import com.umc.umcmission.domain.user.entity.Food;
import com.umc.umcmission.domain.user.entity.Term;
import com.umc.umcmission.domain.user.entity.User;
import com.umc.umcmission.domain.user.entity.mapping.UserFood;
import com.umc.umcmission.domain.user.entity.mapping.UserTerm;
import com.umc.umcmission.domain.user.enums.Role;

public class UserConverter {

  public static User toUser(UserReqDTO.SignupReq request, String encodedPassword) {
    return User.builder()
        .name(request.name())
        .nickname(request.nickname())
        .email(request.email())
        .password(encodedPassword)
        .address(request.address())
        .phoneNumber(request.phoneNumber())
        .birthDate(request.birthDate())
        .gender(request.gender())
        .point(0)
        .role(Role.USER)
        .build();
  }

  public static UserFood toUserFood(User user, Food food) {
    return UserFood.builder()
        .user(user)
        .food(food)
        .build();
  }

  public static UserTerm toUserTerm(User user, Term term) {
    return UserTerm.builder()
        .user(user)
        .term(term)
        .build();
  }
}
