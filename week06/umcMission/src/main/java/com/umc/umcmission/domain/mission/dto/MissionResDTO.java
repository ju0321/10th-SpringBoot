package com.umc.umcmission.domain.mission.dto;

import com.umc.umcmission.domain.mission.enums.MissionStatus;
import com.umc.umcmission.global.apiPayload.page.PageResDTO;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

public class MissionResDTO {

  @Builder
  public record AddMissionRes(
      Long missionId,
      String title,
      Integer rewardPoint
  ) {}

  @Builder
  public record ChallengeMissionRes(
      Long userMissionId,
      MissionStatus status,
      LocalDateTime startedAt
  ) {}

  @Builder
  public record MissionPreview(
      Long userMissionId,
      String missionTitle,
      Integer rewardPoint,
      MissionStatus status,
      String storeName,
      String storeCategory,
      LocalDateTime startedAt
  ) {}

  @Builder
  public record MissionCompleteRes(
      Long userMissionId,
      String verificationCode,
      LocalDateTime completedAt
  ) {}

  @Builder
  public record MissionInfo(
      String storeName,
      String storeCategory,
      String missionContent,
      Integer missionPoint
  ) {}

  @Builder
  public record HomeRes(
      String region,
      Integer successMissionCount,
      PageResDTO.PageRes<MissionInfo> missionPage
  ) {}
}
