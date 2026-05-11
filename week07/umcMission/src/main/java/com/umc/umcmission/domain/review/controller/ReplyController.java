package com.umc.umcmission.domain.review.controller;

import com.umc.umcmission.domain.review.dto.ReplyReqDTO;
import com.umc.umcmission.domain.review.dto.ReplyResDTO;
import com.umc.umcmission.domain.review.exception.code.ReplySuccessCode;
import com.umc.umcmission.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name= "Reply", description = "리뷰 답글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews/{reviewId}/reply")
public class ReplyController {

  @Operation(summary = "답글 작성", description = "리뷰에 답글을 작성합니다.")
  @PostMapping
  public ApiResponse<ReplyResDTO.ReplyRes> addReply(
      @Parameter(description = "리뷰 ID") @PathVariable Long reviewId,
      @RequestBody @Valid ReplyReqDTO.CreateReplyReq request
  ){
    return ApiResponse.onSuccess(ReplySuccessCode.REPLY_CREATED, null);
  }


  @Operation(summary = "답글 수정", description = "리뷰 답글을 수정합니다.")
  @PatchMapping
  public ApiResponse<ReplyResDTO.ReplyRes> updateReply(
      @Parameter(description = "리뷰 ID") @PathVariable Long reviewId,
      @RequestBody @Valid ReplyReqDTO.UpdateReplyReq request
  ) {
    return ApiResponse.onSuccess(ReplySuccessCode.REPLY_UPDATED, null);
  }

  @Operation(summary = "답글 삭제", description = "리뷰 답글을 삭제합니다.")
  @DeleteMapping
  public ApiResponse<Void> deleteReply(
      @Parameter(description = "리뷰 ID") @PathVariable Long reviewId
  ) {
    return ApiResponse.onSuccess(ReplySuccessCode.REPLY_DELETED, null);
  }
}

