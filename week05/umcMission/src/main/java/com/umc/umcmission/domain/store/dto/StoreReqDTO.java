package com.umc.umcmission.domain.store.dto;

import com.umc.umcmission.domain.store.enums.StoreCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class StoreReqDTO {

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class AddStoreReq {

    @NotBlank(message = "가게 이름은 필수입니다.")
    private String storeName;

    @NotNull(message = "가게 카테고리는 필수입니다.")
    private StoreCategory storeCategory;

    @NotBlank(message = "주소는 필수입니다.")
    private String address;

    private String description;
  }
}
