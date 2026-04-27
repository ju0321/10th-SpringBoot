package com.umc.umcmission.domain.mission.dto;

import com.umc.umcmission.domain.mission.enums.MissionStatus;
import java.time.LocalDateTime;
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
}
