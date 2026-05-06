package com.umc.umcmission.domain.mission.service;

import com.umc.umcmission.domain.mission.dto.MissionResDTO.HomeRes;
import com.umc.umcmission.domain.mission.dto.MissionResDTO.MissionInfo;
import com.umc.umcmission.domain.mission.entity.Mission;
import com.umc.umcmission.domain.mission.enums.MissionStatus;
import com.umc.umcmission.domain.mission.repository.MissionRepository;
import com.umc.umcmission.domain.store.entity.Region;
import com.umc.umcmission.domain.store.exception.code.StoreErrorCode;
import com.umc.umcmission.domain.store.repository.RegionRepository;
import com.umc.umcmission.domain.user.entity.User;
import com.umc.umcmission.domain.user.exception.code.UserErrorCode;
import com.umc.umcmission.domain.user.repository.UserRepository;
import com.umc.umcmission.global.apiPayload.exception.ProjectException;
import com.umc.umcmission.global.apiPayload.page.PageResDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MissionService {

  private final MissionRepository missionRepository;
  private final UserRepository userRepository;
  private final RegionRepository regionRepository;


  @Transactional
  public HomeRes getHome(Long userId, Long regionId, int page, int size) {
    //1. 예외처리 및 유저, 지역 찾기
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ProjectException(UserErrorCode.USER_NOT_FOUND));
    Region region = regionRepository.findById(regionId)
        .orElseThrow(() -> new ProjectException(StoreErrorCode.REGION_NOT_FOUND));
    //2. 미션 상태로 성공한 미션 수 가져오기
    int successMissionCount = (int) user.getUserMissions().stream()
        .filter(um -> um.getStatus() == MissionStatus.COMPLETED)
        .count();

    Pageable pageable = PageRequest.of(page, size);
    Page<Mission> missionPage = missionRepository.findAvailableMissions(regionId, userId, pageable);

    return toGetHome(region, successMissionCount, toMissionPage(missionPage));
  }



  private HomeRes toGetHome(Region region, int successMissionCount,
      PageResDTO.PageRes<MissionInfo> missionPage) {
    return HomeRes.builder()
        .region(region.getRegionName())
        .successMissionCount(successMissionCount)
        .missionPage(missionPage)
        .build();
  }

  private PageResDTO.PageRes<MissionInfo> toMissionPage(Page<Mission> missionPage) {
    return PageResDTO.PageRes.of(
        missionPage.map(m -> MissionInfo.builder()
            .storeName(m.getStore().getStoreName())
            .storeCategory(m.getStore().getStoreCategory().name())
            .missionContent(m.getTitle())
            .missionPoint(m.getRewardPoint())
            .build())
    );

  }
}