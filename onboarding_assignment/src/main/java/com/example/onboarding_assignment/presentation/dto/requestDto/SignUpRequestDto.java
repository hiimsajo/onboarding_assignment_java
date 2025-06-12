package com.example.onboarding_assignment.presentation.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {

  private String username;
  private String nickname;
  private String password;

}
