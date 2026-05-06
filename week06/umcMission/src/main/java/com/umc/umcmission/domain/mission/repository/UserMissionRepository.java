package com.umc.umcmission.domain.mission.repository;

import com.umc.umcmission.domain.mission.entity.mapping.UserMission;
import com.umc.umcmission.domain.mission.enums.MissionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMissionRepository extends JpaRepository<UserMission, Long> {

  Page<UserMission> findByUserIdAndStatus(Long userId, MissionStatus status, Pageable pageable);

  boolean existsByUserIdAndMissionId(Long userId, Long missionId);


}
