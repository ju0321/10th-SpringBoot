package com.umc.umcmission.global.apiPayload;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.umc.umcmission.global.apiPayload.code.BaseErrorCode;
import com.umc.umcmission.global.apiPayload.code.BaseSuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"}) //JSON 형식 직렬화 순서 지정
public class ApiResponse<T> { // <T> 제네릭 추가

  @JsonProperty("isSuccess")
  private final Boolean isSuccess;

  @JsonProperty("code")
  private final String code;

  @JsonProperty("message")
  private final String message;

  @JsonProperty("result")
  private final T result;

  public static <T> ApiResponse<T> onSuccess(BaseSuccessCode code, T result) {
    return new ApiResponse<>(true, code.getCode(), code.getMessage(), result);
  }

  public static <T> ApiResponse<T> onFailure(BaseErrorCode code, T result) {
    return new ApiResponse<>(false, code.getCode(), code.getMessage(), result);
  }
}
