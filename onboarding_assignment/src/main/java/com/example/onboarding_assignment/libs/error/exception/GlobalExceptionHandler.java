package com.example.onboarding_assignment.libs.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomAuthenticationException.class)
  public ResponseEntity<ErrorResponseDto> handleCustomAuthException(CustomAuthenticationException ex) {
    ErrorResponseDto response = new ErrorResponseDto(
        new ErrorResponseDto.ErrorDetail(ex.getCode(), ex.getMessage())
    );
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
  }

  @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
  public ResponseEntity<ErrorResponseDto> handleAccessDeniedException(org.springframework.security.access.AccessDeniedException ex) {
    ErrorResponseDto response = new ErrorResponseDto(
        new ErrorResponseDto.ErrorDetail("ACCESS_DENIED", "관리자 권한이 필요한 요청입니다. 접근 권한이 없습니다.")
    );
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
  }

}
