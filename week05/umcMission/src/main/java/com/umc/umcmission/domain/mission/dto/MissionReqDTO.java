package com.umc.umcmission.domain.mission.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public class MissionReqDTO {

  @Builder
  public record AddMissionReq(
      @NotBlank(message = "미션 제목은 필수입니다.") String title,
      String description,
      @NotNull(message = "보상 포인트는 필수입니다.") @Min(value = 0, message = "보상 포인트는 0 이상이어야 합니다.") Integer rewardPoint
  ) {}

  @Builder
  public record ChallengeMissionReq(
      @NotNull(message = "유저 ID는 필수입니다.") Long userId
  ) {}
}
