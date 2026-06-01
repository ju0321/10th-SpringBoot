package com.umc.umcmission.domain.user.repository;

import com.umc.umcmission.domain.user.entity.mapping.UserFood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFoodRepository extends JpaRepository<UserFood, Long> {
}
