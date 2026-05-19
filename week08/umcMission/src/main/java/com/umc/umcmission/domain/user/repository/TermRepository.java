package com.umc.umcmission.domain.user.repository;

import com.umc.umcmission.domain.user.entity.Term;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermRepository extends JpaRepository<Term, Long> {

  List<Term> findAllByIsOptionalFalse();
}
