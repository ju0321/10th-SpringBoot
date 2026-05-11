package com.umc.umcmission.domain.mission.repository;

import com.umc.umcmission.domain.mission.entity.Mission;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MissionRepository extends JpaRepository<Mission, Long> {
  @Query("SELECT m FROM Mission m " +
      "WHERE m.store.region.id = :regionId " +
      "AND m.id NOT IN (SELECT um.mission.id FROM UserMission um WHERE um.user.id = :userId)")
  Page<Mission> findAvailableMissions(
      @Param("regionId") Long regionId,
      @Param("userId") Long userId,
      Pageable pageable
  );

  Page<Mission> findByStoreId(Long storeId, Pageable pageable);

  // Slice 기반 커서 - 첫 페이지
  Slice<Mission> findMissionsByStoreId_OrderByIdDesc(Long storeId, Pageable pageable);

  // Slice 기반 커서 - 이후 페이지 (id < cursor)
  Slice<Mission> findMissionsByStoreId_AndIdLessThanOrderByIdDesc(Long storeId, Long id, Pageable pageable);
}
