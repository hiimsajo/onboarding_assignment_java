package com.example.onboarding_assignment.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {

  USER,
  ADMIN;

  // 권한문자열 메서드
  public String getAuthority() {
    return name();
  }
}
