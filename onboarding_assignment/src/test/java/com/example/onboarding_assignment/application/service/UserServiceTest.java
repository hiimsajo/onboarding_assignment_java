package com.example.onboarding_assignment.application.service;


import com.example.onboarding_assignment.domain.model.User;
import com.example.onboarding_assignment.domain.repository.UserRepository;
import com.example.onboarding_assignment.presentation.dto.requestDto.SignUpRequestDto;
import com.example.onboarding_assignment.presentation.dto.responseDto.UserResponseDto;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Test
  void testCreateUser() {
    // given
    SignUpRequestDto requestDto = new SignUpRequestDto("Gamja10", "Mentos", "password1234");
    requestDto.setUsername("Gamja10");
    requestDto.setNickname("Mentos");
    requestDto.setPassword("password1234");

    // 실제로 암호화된 값 생성
    String encodedPassword = passwordEncoder.encode("password1234");

    // when
    when(passwordEncoder.encode(anyString())).thenReturn(encodedPassword);

    // userService를 호출하여 유저 생성
    UserResponseDto responseDto = userService.createUser(requestDto);

    // then
    assertNotNull(responseDto);
    assertEquals("Gamja10", responseDto.getUsername());
    assertEquals("Mentos", responseDto.getNickname());
    assertEquals("ROLE_USER", responseDto.getAuthorities().get(0).getAuthorityName());

    Optional<User> savedUser = userRepository.findByUsername("Gamja10");
    assertTrue(savedUser.isPresent());

  }
}