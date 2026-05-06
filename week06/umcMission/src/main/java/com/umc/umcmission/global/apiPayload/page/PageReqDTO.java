package com.umc.umcmission.global.apiPayload.page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageReqDTO {

  public record PageReq(int page, int size) {

    public Pageable toPageable() {
      return PageRequest.of(page, size);
    }
  }
}
