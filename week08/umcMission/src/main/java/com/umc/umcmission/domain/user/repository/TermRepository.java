package com.umc.umcmission.domain.user.repository;

import com.umc.umcmission.domain.user.entity.Term;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermRepository extends JpaRepository<Term, Long> {
}
