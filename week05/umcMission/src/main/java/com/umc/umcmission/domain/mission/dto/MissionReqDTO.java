package com.umc.umcmission.domain.mission.dto;

import com.umc.umcmission.domain.mission.enums.MissionStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MissionReqDTO {

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class AddMissionReq {

    @NotBlank(message = "미션 제목은 필수입니다.")
    private String title;

    private String description;

    @NotNull(message = "보상 포인트는 필수입니다.")
    @Min(value = 0, message = "보상 포인트는 0 이상이어야 합니다.")
    private Integer rewardPoint;
  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ChallengeMissionReq {

    @NotNull(message = "유저 ID는 필수입니다.")
    private Long userId;
  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UpdateMissionStatusReq {

    @NotNull(message = "미션 상태는 필수입니다.")
    private MissionStatus status;
  }
}
