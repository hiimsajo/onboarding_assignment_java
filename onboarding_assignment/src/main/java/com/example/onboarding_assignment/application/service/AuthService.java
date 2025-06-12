package com.example.onboarding_assignment.application.service;

import com.example.onboarding_assignment.domain.model.User;
import com.example.onboarding_assignment.infrastructure.details.CustomUserDetails;
import com.example.onboarding_assignment.infrastructure.jwt.JwtTokenizer;
import com.example.onboarding_assignment.libs.error.exception.CustomAuthenticationException;
import com.example.onboarding_assignment.presentation.dto.requestDto.LogInRequestDto;
import org.springframework.security.core.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenizer jwtTokenizer;

  public String login(LogInRequestDto logInRequestDto) {
    try {
      // AuthenticationManager로 인증 처리
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              logInRequestDto.getUsername(),
              logInRequestDto.getPassword()
          )
      );

      // 인증 성공 시 사용자 정보와 역할 추출
      CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
      String username = customUserDetails.getUsername();
      String role = customUserDetails.getAuthorities().iterator().next().getAuthority();

      // JWT 생성
      return jwtTokenizer.generateAccessToken(username, role);

    } catch (AuthenticationException e) {
      throw new CustomAuthenticationException("INVALID_CREDENTIALS", "아이디 또는 비밀번호가 올바르지 않습니다.");
    }
  }

}
