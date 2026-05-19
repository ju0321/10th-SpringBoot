package com.umc.umcmission.domain.user.service;

import com.umc.umcmission.domain.user.converter.UserConverter;
import com.umc.umcmission.domain.user.dto.UserReqDTO;
import com.umc.umcmission.domain.user.dto.UserResDTO;
import com.umc.umcmission.domain.user.dto.UserResDTO.MyPageRes;
import com.umc.umcmission.domain.user.entity.Food;
import com.umc.umcmission.domain.user.entity.Term;
import com.umc.umcmission.domain.user.entity.User;
import com.umc.umcmission.domain.user.entity.mapping.UserFood;
import com.umc.umcmission.domain.user.entity.mapping.UserTerm;
import java.util.List;
import com.umc.umcmission.domain.user.exception.code.UserErrorCode;
import com.umc.umcmission.domain.user.repository.FoodRepository;
import com.umc.umcmission.domain.user.repository.TermRepository;
import com.umc.umcmission.domain.user.repository.UserFoodRepository;
import com.umc.umcmission.domain.user.repository.UserRepository;
import com.umc.umcmission.domain.user.repository.UserTermRepository;
import com.umc.umcmission.global.apiPayload.exception.ProjectException;
import com.umc.umcmission.global.security.entity.AuthMember;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final TermRepository termRepository;
  private final FoodRepository foodRepository;
  private final UserTermRepository userTermRepository;
  private final UserFoodRepository userFoodRepository;
  private final PasswordEncoder passwordEncoder;

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

  @Transactional
  public MyPageRes updateUser(Long userId, UserReqDTO.UpdateUserReq request) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ProjectException(UserErrorCode.USER_NOT_FOUND));
    user.updateInfo(request.nickname(), request.address(), request.phoneNumber());
    return MyPageRes.builder()
        .nickName(user.getNickname())
        .email(user.getEmail())
        .phoneNumber(user.getPhoneNumber())
        .point(user.getPoint())
        .build();
  }

  @Transactional
  public UserResDTO.SignupRes signup(UserReqDTO.SignupReq request) {
    if (userRepository.existsByEmail(request.email())) {
      throw new ProjectException(UserErrorCode.EMAIL_ALREADY_EXISTS);
    }

    List<Long> requiredTermIds = termRepository.findAllByIsOptionalFalse()
        .stream().map(Term::getId).toList();
    List<Long> agreedTermIds = request.termIds() != null ? request.termIds() : List.of();
    if (!agreedTermIds.containsAll(requiredTermIds)) {
      throw new ProjectException(UserErrorCode.REQUIRED_TERM_NOT_AGREED);
    }

    String encodedPassword = passwordEncoder.encode(request.password());
    User user = userRepository.save(UserConverter.toUser(request, encodedPassword));

    if (request.termIds() != null && !request.termIds().isEmpty()) {
      List<UserTerm> userTerms = request.termIds().stream()
          .map(termId -> termRepository.findById(termId)
              .orElseThrow(() -> new ProjectException(UserErrorCode.TERM_NOT_FOUND)))
          .map(term -> UserConverter.toUserTerm(user, term))
          .toList();
      userTermRepository.saveAll(userTerms);
    }

    if (request.foodIds() != null && !request.foodIds().isEmpty()) {
      List<UserFood> userFoods = request.foodIds().stream()
          .map(foodId -> foodRepository.findById(foodId)
              .orElseThrow(() -> new ProjectException(UserErrorCode.FOOD_CATEGORY_NOT_FOUND)))
          .map(food -> UserConverter.toUserFood(user, food))
          .toList();
      userFoodRepository.saveAll(userFoods);
    }

    return UserResDTO.SignupRes.builder()
        .id(user.getId())
        .email(user.getEmail())
        .name(user.getName())
        .build();
  }

  @Transactional(readOnly = true)
  public UserResDTO.LoginRes login(UserReqDTO.LoginReq request, HttpServletRequest httpRequest) {
    User user = userRepository.findByEmail(request.email())
        .orElseThrow(() -> new ProjectException(UserErrorCode.INVALID_CREDENTIALS));

    if (!passwordEncoder.matches(request.password(), user.getPassword())) {
      throw new ProjectException(UserErrorCode.INVALID_CREDENTIALS);
    }

    AuthMember authMember = new AuthMember(user);
    UsernamePasswordAuthenticationToken auth =
        new UsernamePasswordAuthenticationToken(authMember, null, authMember.getAuthorities());

    SecurityContextHolder.getContext().setAuthentication(auth);

    HttpSession session = httpRequest.getSession(true);
    session.setAttribute(
        HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
        SecurityContextHolder.getContext()
    );

    return UserResDTO.LoginRes.builder()
        .id(user.getId())
        .email(user.getEmail())
        .name(user.getName())
        .role(user.getRole().name())
        .build();
  }

  @Transactional
  public void withdraw(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ProjectException(UserErrorCode.USER_NOT_FOUND));
    userRepository.delete(user);
  }
}
