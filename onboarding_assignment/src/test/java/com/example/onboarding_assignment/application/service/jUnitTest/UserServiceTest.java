package com.example.onboarding_assignment.application.service.jUnitTest;

import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import com.example.onboarding_assignment.application.service.UserService;
import com.example.onboarding_assignment.domain.model.User;
import com.example.onboarding_assignment.domain.repository.UserRepository;
import com.example.onboarding_assignment.presentation.dto.requestDto.SignUpRequestDto;
import com.example.onboarding_assignment.presentation.dto.responseDto.UserResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceTest {

  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;
  private UserService userService;

  @BeforeEach
  void setUp() {
    userRepository = mock(UserRepository.class);
    passwordEncoder = mock(PasswordEncoder.class);
    userService = new UserService(userRepository, passwordEncoder);
  }

  @Test
  @DisplayName("회원가입 성공")
  void createUserSuccess() {
    // given
    SignUpRequestDto reqDto = new SignUpRequestDto("testgamja", "testnick", "testpw");
    when(userRepository.existsByUsernameAndIsDeletedFalse("testgamja")).thenReturn(false);
    when(passwordEncoder.encode("testpw")).thenReturn("encodedpw");
    User userEntity = User.from(reqDto, "encodedpw");
    when(userRepository.save(any(User.class))).thenReturn(userEntity);

    // when
    UserResponseDto result = userService.createUser(reqDto);

    // then
    assertNotNull(result);
    assertEquals("testgamja", result.getUsername());
    verify(userRepository, times(2)).save(any(User.class)); // 저장이 두 번 호출됨
  }

  @Test
  @DisplayName("이미 존재하는 유저네임이면 예외 발생")
  void duplicateUsernameThrowsException() {
    // given
    SignUpRequestDto reqDto = new SignUpRequestDto("testgamja2", "testname2", "testpw");
    when(userRepository.existsByUsernameAndIsDeletedFalse("testgamja2")).thenReturn(true);

    // when & then
    RuntimeException ex = assertThrows(RuntimeException.class, () -> userService.createUser(reqDto));
    assertEquals("이미 존재하는 유저네임입니다.", ex.getMessage());
  }

}
