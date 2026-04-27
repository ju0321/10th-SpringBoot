package com.umc.umcmission.domain.user.dto;

import java.util.List;
import lombok.Builder;

public class UserResDTO {

  @Builder
  public record LoginRes(
      Long userId,
      String name
  ) {}

  @Builder
  public record Home(
      String region,
      Integer successMissionCount,
      List<MissionInfo> missions
  ) {}

  @Builder
  public record MissionInfo(
      String storeName,
      String storeCategory,
      String missionContent,
      Integer missionPoint
  ) {}

  @Builder
  public record MyPageRes(
      String nickName,
      String email,
      String phoneNumber,
      Integer point
  ) {}
}
