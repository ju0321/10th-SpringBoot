package com.umc.umcmission.domain.mission.dto;

import com.umc.umcmission.domain.mission.enums.MissionStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MissionResDTO {

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class AddMissionRes {
    private Long missionId;
    private String title;
    private Integer rewardPoint;
  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ChallengeMissionRes {
    private Long userMissionId;
    private MissionStatus status;
    private LocalDateTime startedAt;
  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class MissionPreview {
    private Long userMissionId;
    private String missionTitle;
    private Integer rewardPoint;
    private MissionStatus status;
    private String storeName;
    private LocalDateTime startedAt;
  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class MissionPreviewListRes {
    private List<MissionPreview> missionList;
    private int listSize;
    private int totalPage;
    private long totalElements;
    private boolean isFirst;
    private boolean isLast;
  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UpdateMissionStatusRes {
    private Long userMissionId;
    private MissionStatus status;
    private LocalDateTime completedAt;
  }
}
