package com.example.onboarding_assignment.application.service.jUnitTest;

import static com.example.onboarding_assignment.domain.model.UserRole.USER;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.onboarding_assignment.application.service.AuthService;
import com.example.onboarding_assignment.domain.model.User;
import com.example.onboarding_assignment.infrastructure.details.CustomUserDetails;
import com.example.onboarding_assignment.infrastructure.jwt.JwtTokenizer;
import com.example.onboarding_assignment.libs.error.exception.CustomAuthenticationException;
import com.example.onboarding_assignment.presentation.dto.requestDto.LogInRequestDto;
import java.util.Collections;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class AuthServiceTest {

  private AuthenticationManager authenticationManager;
  private JwtTokenizer jwtTokenizer;
  private AuthService authService;

  @BeforeEach
  void setUp() {
    authenticationManager = mock(AuthenticationManager.class);
    jwtTokenizer = mock(JwtTokenizer.class);
    authService = new AuthService(authenticationManager, jwtTokenizer);
  }

  @Test
  @DisplayName("로그인 성공 시 JWT 토큰 반환")
  void login_success_returnsToken() {
    // given
    LogInRequestDto reqDto = new LogInRequestDto("testuser", "testpw");
    UUID userId = UUID.fromString("67c4a1ef-7b30-45da-a328-0902b8d10444");
    User user = new User(userId, "testuser", "testnick2", "testpw", USER); // User 생성자에 맞춰 작성
    CustomUserDetails userDetails = new CustomUserDetails(user);

    Authentication authentication = mock(Authentication.class);
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(authentication);
    when(authentication.getPrincipal()).thenReturn(userDetails);
    when(jwtTokenizer.generateAccessToken("ROLE_USER", "testuser")).thenReturn("mocked.jwt.token");

    // when
    String token = authService.login(reqDto);

    // then
    assertEquals("mocked.jwt.token", token);
  }

  @Test
  @DisplayName("로그인 실패 시 CustomAuthenticationException 발생")
  void login_fail_throwsException() {
    // given
    LogInRequestDto reqDto = new LogInRequestDto("testuser", "wrongpw");
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenThrow(new org.springframework.security.core.AuthenticationException("Bad credentials") {});

    // when & then
    assertThrows(CustomAuthenticationException.class, () -> authService.login(reqDto));
  }

}
