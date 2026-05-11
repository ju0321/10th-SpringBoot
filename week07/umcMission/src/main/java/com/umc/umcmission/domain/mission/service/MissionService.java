package com.umc.umcmission.domain.mission.service;

import com.umc.umcmission.domain.mission.dto.MissionReqDTO;
import com.umc.umcmission.domain.mission.dto.MissionResDTO.CreateMissionRes;
import com.umc.umcmission.domain.mission.dto.MissionResDTO.GetMission;
import com.umc.umcmission.domain.mission.dto.MissionResDTO.HomeRes;
import com.umc.umcmission.domain.mission.dto.MissionResDTO.MissionInfo;
import com.umc.umcmission.domain.mission.dto.MissionResDTO.Pagination;
import com.umc.umcmission.domain.mission.entity.Mission;
import com.umc.umcmission.domain.mission.enums.MissionStatus;
import com.umc.umcmission.domain.mission.exception.MissionException;
import com.umc.umcmission.domain.mission.exception.code.MissionErrorCode;
import com.umc.umcmission.domain.mission.repository.MissionRepository;
import com.umc.umcmission.domain.store.entity.Region;
import com.umc.umcmission.domain.store.entity.Store;
import com.umc.umcmission.domain.store.exception.code.StoreErrorCode;
import com.umc.umcmission.domain.store.repository.RegionRepository;
import com.umc.umcmission.domain.store.repository.StoreRepository;
import com.umc.umcmission.domain.user.entity.User;
import com.umc.umcmission.domain.user.exception.code.UserErrorCode;
import com.umc.umcmission.domain.user.repository.UserRepository;
import com.umc.umcmission.global.apiPayload.exception.ProjectException;
import com.umc.umcmission.global.apiPayload.page.PageResDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MissionService {

  private final MissionRepository missionRepository;
  private final UserRepository userRepository;
  private final RegionRepository regionRepository;
  private final StoreRepository storeRepository;


  @Transactional
  public CreateMissionRes createMission(Long storeId, MissionReqDTO.CreateMissionReq dto) {
    Store store = storeRepository.findById(storeId)
        .orElseThrow(() -> new ProjectException(StoreErrorCode.STORE_NOT_FOUND));

    Mission mission = Mission.builder()
        .title(dto.title())
        .description(dto.description())
        .rewardPoint(dto.rewardPoint())
        .store(store)
        .build();

    Mission saved = missionRepository.save(mission);

    return CreateMissionRes.builder()
        .missionId(saved.getId())
        .title(saved.getTitle())
        .rewardPoint(saved.getRewardPoint())
        .build();
  }

  @Transactional(readOnly = true)
  public Page<GetMission> getMissions(Long storeId, Integer pageSize, Integer pageNum, String sort) {
    if (!storeRepository.existsById(storeId)) {
      throw new ProjectException(StoreErrorCode.STORE_NOT_FOUND);
    }

    Sort sorting = (sort != null && sort.equalsIgnoreCase("rewardPoint"))
        ? Sort.by(Sort.Direction.DESC, "rewardPoint")
        : Sort.by(Sort.Direction.ASC, "id");

    Pageable pageable = PageRequest.of(pageNum, pageSize, sorting);

    return missionRepository.findByStoreId(storeId, pageable)
        .map(m -> GetMission.builder()
            .missionId(m.getId())
            .title(m.getTitle())
            .description(m.getDescription())
            .rewardPoint(m.getRewardPoint())
            .build());
  }

  @Transactional(readOnly = true)
  public Pagination<GetMission> getMissions2(Long storeId, Integer pageSize, String cursor, String query) {
    if (!storeRepository.existsById(storeId)) {
      throw new ProjectException(StoreErrorCode.STORE_NOT_FOUND);
    }

    // 1. 페이지 정보로 PageRequest 만들기
    Pageable pageable = PageRequest.of(0, pageSize);

    Long idCursor;
    Slice<Mission> missionList;
    String nextCursor;

    // 2. 커서 유무 확인
    if (!cursor.equals("-1")) {
      // 2a. 커서 있는 경우 → 커서 분리 & 타입 변환
      String[] cursorSplit = cursor.split(":");
      switch (query.toLowerCase()) {
        case "id":
          Long prevCursor = Long.valueOf(cursorSplit[0]);
          idCursor = Long.parseLong(cursorSplit[1]);
          // 가게 내 미션들 조회 & where절에 커서값 기입
          missionList = missionRepository.findMissionsByStoreId_AndIdLessThanOrderByIdDesc(storeId, idCursor, pageable);
          break;
        default:
          throw new MissionException(MissionErrorCode.QUERY_NOT_VALID);
      }
    } else {
      // 2b. 커서 없이 조회
      missionList = missionRepository.findMissionsByStoreId_OrderByIdDesc(storeId, pageable);
    }

    // 3. 다음 커서 계산 - ID:ID 형태로 제작
    Mission lastMission = missionList.getContent().isEmpty() ? null : missionList.getContent().getLast();
    nextCursor = (lastMission != null)
        ? lastMission.getId() + ":" + lastMission.getId()
        : null;

    // 4. 미션들 응답 DTO로 포장하기
    return toPagination(missionList, pageSize, nextCursor);
  }

  private Pagination<GetMission> toPagination(Slice<Mission> missionSlice, Integer pageSize, String nextCursor) {
    List<GetMission> data = missionSlice.getContent().stream()
        .map(m -> GetMission.builder()
            .missionId(m.getId())
            .title(m.getTitle())
            .description(m.getDescription())
            .rewardPoint(m.getRewardPoint())
            .build())
        .toList();

    return Pagination.<GetMission>builder()
        .data(data)
        .pageSize(pageSize)
        .nextCursor(nextCursor)
        .hasNext(missionSlice.hasNext())
        .build();
  }

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