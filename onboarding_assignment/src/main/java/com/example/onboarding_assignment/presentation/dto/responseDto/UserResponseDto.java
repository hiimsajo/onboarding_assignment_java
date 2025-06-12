package com.example.onboarding_assignment.presentation.dto.responseDto;

import com.example.onboarding_assignment.domain.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDto {

  @JsonIgnore // userId를 반환하지 않도록 숨기기
  private UUID userId;
  private String username;
  private String nickname;
  private List<AuthorityDto> authorities;

  public static UserResponseDto from(User user){
    return UserResponseDto.builder()
        .userId(user.getUserId())
        .username(user.getUsername())
        .nickname(user.getNickname())
        .authorities(List.of(AuthorityDto.from(user.getUserRole())))
        .build();
  }
}
