package com.umc.umcmission.domain.user.repository;

import com.umc.umcmission.domain.user.entity.Food;
import com.umc.umcmission.domain.user.enums.FoodCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {

  Optional<Food> findByFoodCategory(FoodCategory foodCategory);
}
