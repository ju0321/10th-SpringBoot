package com.umc.umcmission.global.apiPayload.exception;

import com.umc.umcmission.global.apiPayload.ApiResponse;
import com.umc.umcmission.global.apiPayload.code.BaseErrorCode;
import com.umc.umcmission.global.apiPayload.code.GeneralErrorCode;
import com.umc.umcmission.global.apiPayload.exception.ProjectException;
import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GeneralExceptionAdvice {

  // 프로젝트에서 정의한 예외 처리
  @ExceptionHandler(ProjectException.class)
  public ResponseEntity<ApiResponse<Void>> handleProjectException(ProjectException e) {
    BaseErrorCode errorCode = e.getErrorCode();
    return ResponseEntity.status(errorCode.getStatus())
        .body(ApiResponse.onFailure(errorCode, null));
  }

  // @RequestParam, @PathVariable 검증 실패 예외 처리 (@Validated 사용 시)
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ApiResponse<String>> handleConstraintViolationException(
      ConstraintViolationException e) {
    String errorMessage = e.getConstraintViolations().stream()
        .map(v -> v.getPropertyPath() + ": " + v.getMessage())
        .collect(Collectors.joining(", "));
    BaseErrorCode code = GeneralErrorCode.BAD_REQUEST;
    return ResponseEntity.status(code.getStatus())
        .body(ApiResponse.onFailure(code, errorMessage));
  }

  // 그 외 정의되지 않은 모든 예외 처리
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<String>> handleException(Exception e) {
    BaseErrorCode code = GeneralErrorCode.INTERNAL_SERVER_ERROR;
    return ResponseEntity.status(code.getStatus())
        .body(ApiResponse.onFailure(code, e.getMessage()));
  }

  // @Valid 어노테이션 검증 실패 예외
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Map<String, String>>> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e
  ){
    // 검증 실패한 변수명과 실패 이유를 담을 Map
    Map<String, String> errors = new HashMap<>();
    e.getBindingResult().getFieldErrors().forEach(error -> {
      errors.put(error.getField(), error.getDefaultMessage());
    });

    BaseErrorCode code = GeneralErrorCode.BAD_REQUEST;
    return ResponseEntity.status(code.getStatus())
        .body(ApiResponse.onFailure(code, errors));
  }
}
