package com.example.onboarding_assignment.presentation.dto.requestDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class SignUpRequestDto {

  private String username;
  private String nickname;
  private String password;

  // public 생성자 추가
  public SignUpRequestDto(String username, String nickname, String password) {
    this.username = username;
    this.nickname = nickname;
    this.password = password;
  }

}
