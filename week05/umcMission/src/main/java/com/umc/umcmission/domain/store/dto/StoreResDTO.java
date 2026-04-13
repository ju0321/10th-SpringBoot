package com.umc.umcmission.domain.store.dto;

import com.umc.umcmission.domain.store.enums.StoreCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class StoreResDTO {

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class AddStoreRes {
    private Long storeId;
    private String storeName;
  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class StoreInfoRes {
    private Long storeId;
    private String storeName;
    private StoreCategory storeCategory;
    private String address;
    private String description;
    private String regionName;
  }
}
