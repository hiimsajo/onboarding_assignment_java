package com.example.onboarding_assignment.presentation.dto.responseDto;

import com.example.onboarding_assignment.domain.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthorityDto {

  private final String authorityName;

  // UserRole 객체를 받아서 ROLE_ 접두사 붙이는 메서드를 여기서 담당
  public static AuthorityDto from(UserRole userRole) {
    return new AuthorityDto("ROLE_" + userRole.USER.name());
  }

}
