package com.umc.umcmission.domain.mission.repository;

import com.umc.umcmission.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

}
