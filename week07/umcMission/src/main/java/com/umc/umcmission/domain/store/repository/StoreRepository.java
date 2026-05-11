package com.umc.umcmission.domain.store.repository;

import com.umc.umcmission.domain.store.entity.Store;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {

  Optional<Store> findById(Long storeId);

}
