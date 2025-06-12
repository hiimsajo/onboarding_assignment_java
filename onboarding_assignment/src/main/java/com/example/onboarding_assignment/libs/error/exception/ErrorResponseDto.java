package com.example.onboarding_assignment.libs.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponseDto {

  private ErrorDetail error;

  @Getter
  @AllArgsConstructor
  public static class ErrorDetail {
    private String code;
    private String message;
  }

}
