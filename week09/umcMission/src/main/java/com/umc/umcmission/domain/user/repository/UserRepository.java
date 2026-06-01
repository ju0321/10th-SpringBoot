package com.umc.umcmission.domain.user.repository;

import com.umc.umcmission.domain.user.entity.User;
import com.umc.umcmission.domain.user.enums.SocialType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findById(Long userId);

  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);

  Optional<User> findBySocialTypeAndSocialUid(SocialType socialType, String socialUid);

}
