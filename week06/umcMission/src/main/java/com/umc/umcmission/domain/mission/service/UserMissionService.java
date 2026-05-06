package com.umc.umcmission.domain.mission.service;

import com.umc.umcmission.domain.mission.dto.MissionResDTO;
import com.umc.umcmission.domain.mission.entity.mapping.UserMission;
import com.umc.umcmission.domain.mission.enums.MissionStatus;
import com.umc.umcmission.domain.mission.repository.UserMissionRepository;
import com.umc.umcmission.domain.user.exception.code.UserErrorCode;
import com.umc.umcmission.domain.user.repository.UserRepository;
import com.umc.umcmission.global.apiPayload.exception.ProjectException;
import com.umc.umcmission.global.apiPayload.page.PageResDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserMissionService {

  private final UserMissionRepository userMissionRepository;
  private final UserRepository userRepository;

  @Transactional(readOnly = true)
  public PageResDTO.PageRes<MissionResDTO.MissionPreview> getMyMissions(Long userId, MissionStatus status, int page, int size) {
    if (!userRepository.existsById(userId)) {
      throw new ProjectException(UserErrorCode.USER_NOT_FOUND);
    }

    Pageable pageable = PageRequest.of(page, size);

    return PageResDTO.PageRes.of(
        userMissionRepository.findByUserIdAndStatus(userId, status, pageable)
            .map(this::toMissionPreview)
    );
  }

  private MissionResDTO.MissionPreview toMissionPreview(UserMission userMission) {
    return MissionResDTO.MissionPreview.builder()
        .userMissionId(userMission.getId())
        .missionTitle(userMission.getMission().getTitle())
        .rewardPoint(userMission.getMission().getRewardPoint())
        .status(userMission.getStatus())
        .storeName(userMission.getMission().getStore().getStoreName())
        .storeCategory(userMission.getMission().getStore().getStoreCategory().name())
        .startedAt(userMission.getStartedAt())
        .build();
  }
}
