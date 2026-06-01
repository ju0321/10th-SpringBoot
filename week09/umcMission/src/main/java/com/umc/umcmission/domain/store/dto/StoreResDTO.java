package com.umc.umcmission.domain.store.dto;

import com.umc.umcmission.domain.store.enums.StoreCategory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

public class StoreResDTO {

  @Builder
  public record AddStoreRes(
      Long storeId,
      String storeName
  ) {}

  // 지도 - 가게 리스트 단건
  @Builder
  public record StoreListRes(
      Long storeId,
      String storeName,
      StoreCategory storeCategory,
      Integer missionPoint,
      Double distance
  ) {}


  // 가게 상세 - 리뷰 단건
  @Builder
  public record ReviewInfo(
      String nickname,
      Integer rating,
      String content,
      String imageUrl,
      LocalDateTime createdAt
  ) {}

  // 가게 상세
  @Builder
  public record StoreInfoRes(
      Long storeId,
      String storeName,
      StoreCategory storeCategory,
      String address,
      List<ReviewInfo> reviews
  ) {}
}
