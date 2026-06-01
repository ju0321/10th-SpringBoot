package com.umc.umcmission.global.apiPayload.page;

import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Page;

public class PageResDTO {

  @Builder
  public record PageRes<T>(
      List<T> content,
      int currentPage,
      int totalPages,
      boolean hasNext
  ) {

    public static <T> PageRes<T> of(Page<T> page) {
      return PageRes.<T>builder()
          .content(page.getContent())
          .currentPage(page.getNumber())
          .totalPages(page.getTotalPages())
          .hasNext(page.hasNext())
          .build();
    }
  }
}
