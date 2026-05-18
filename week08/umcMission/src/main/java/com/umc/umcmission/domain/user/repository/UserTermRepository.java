package com.umc.umcmission.domain.user.repository;

import com.umc.umcmission.domain.user.entity.mapping.UserTerm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTermRepository extends JpaRepository<UserTerm, Long> {
}
