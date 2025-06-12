package com.example.onboarding_assignment.libs.error.exception;

import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationException extends AuthenticationException {

  private final String code;

  public CustomAuthenticationException(String code, String message) {
    super(message);
    this.code = code;
  }

  public CustomAuthenticationException(String code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  public String getCode() {
    return code;
  }
  /*
  public CustomAuthenticationException(String message) {
    super(message);
  }

  public CustomAuthenticationException(String message, Throwable cause) {
    super(message, cause);
  }
   */
}
