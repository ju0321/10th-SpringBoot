package com.umc.umcmission.domain.user.service;

import com.umc.umcmission.domain.user.converter.UserConverter;
import com.umc.umcmission.domain.user.dto.UserReqDTO;
import com.umc.umcmission.domain.user.dto.UserResDTO.JoinRes;
import com.umc.umcmission.domain.user.dto.UserResDTO.LoginRes;
import com.umc.umcmission.domain.user.dto.UserResDTO.MyPageRes;
import com.umc.umcmission.domain.user.entity.Food;
import com.umc.umcmission.domain.user.entity.Term;
import com.umc.umcmission.domain.user.entity.User;
import com.umc.umcmission.domain.user.exception.code.UserErrorCode;
import com.umc.umcmission.domain.user.repository.FoodRepository;
import com.umc.umcmission.domain.user.repository.TermRepository;
import com.umc.umcmission.domain.user.repository.UserFoodRepository;
import com.umc.umcmission.domain.user.repository.UserRepository;
import com.umc.umcmission.domain.user.repository.UserTermRepository;
import com.umc.umcmission.global.apiPayload.exception.ProjectException;
import com.umc.umcmission.global.security.CustomUserDetails;
import com.umc.umcmission.global.security.JwtProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final TermRepository termRepository;
  private final FoodRepository foodRepository;
  private final UserTermRepository userTermRepository;
  private final UserFoodRepository userFoodRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;

  @Transactional
  public JoinRes join(UserReqDTO.JoinReq request) {
    if (!request.password().equals(request.passwordConfirm())) {
      throw new ProjectException(UserErrorCode.PASSWORDS_NOT_MATCHING);
    }
    if (userRepository.existsByEmail(request.email())) {
      throw new ProjectException(UserErrorCode.EMAIL_ALREADY_EXISTS);
    }

    String encodedPassword = passwordEncoder.encode(request.password());
    User user = userRepository.save(UserConverter.toUser(request, encodedPassword));

    // 약관 동의 저장
    if (request.agreedTermIds() != null) {
      List<Term> terms = request.agreedTermIds().stream()
          .map(termId -> termRepository.findById(termId)
              .orElseThrow(() -> new ProjectException(UserErrorCode.TERM_NOT_FOUND)))
          .toList();
      terms.forEach(term -> userTermRepository.save(UserConverter.toUserTerm(user, term)));
    }

    // 선호 음식 카테고리 저장
    if (request.foodCategories() != null) {
      List<Food> foods = request.foodCategories().stream()
          .map(category -> foodRepository.findByFoodCategory(category)
              .orElseThrow(() -> new ProjectException(UserErrorCode.FOOD_CATEGORY_NOT_FOUND)))
          .toList();
      foods.forEach(food -> userFoodRepository.save(UserConverter.toUserFood(user, food)));
    }

    return JoinRes.builder()
        .userId(user.getId())
        .name(user.getName())
        .build();
  }

  @Transactional(readOnly = true)
  public LoginRes login(UserReqDTO.LoginReq request) {
    User user = userRepository.findByEmail(request.email())
        .orElseThrow(() -> new ProjectException(UserErrorCode.USER_NOT_FOUND));
    if (!passwordEncoder.matches(request.password(), user.getPassword())) {
      throw new ProjectException(UserErrorCode.PASSWORD_MISMATCH);
    }
    String accessToken = jwtProvider.createAccessToken(new CustomUserDetails(user));
    return LoginRes.builder()
        .userId(user.getId())
        .name(user.getName())
        .accessToken(accessToken)
        .build();
  }

  @Transactional(readOnly = true)
  public MyPageRes getMyPage(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ProjectException(UserErrorCode.USER_NOT_FOUND));
    return MyPageRes.builder()
        .nickName(user.getNickname())
        .email(user.getEmail())
        .phoneNumber(user.getPhoneNumber())
        .point(user.getPoint())
        .build();
  }
}
