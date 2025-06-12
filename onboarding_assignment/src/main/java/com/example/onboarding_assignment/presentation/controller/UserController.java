package com.example.onboarding_assignment.presentation.controller;

import com.example.onboarding_assignment.application.service.AuthService;
import com.example.onboarding_assignment.application.service.UserService;
import com.example.onboarding_assignment.presentation.dto.requestDto.LogInRequestDto;
import com.example.onboarding_assignment.presentation.dto.requestDto.SignUpRequestDto;
import com.example.onboarding_assignment.presentation.dto.responseDto.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
@Tag(name = "User", description = "유저 관련 API")
public class UserController {

  private final UserService userService;
  private final AuthService authService;

  @PostMapping("/signup")
  @Operation(summary = "회원가입", description = "회원가입입니다. 요청 본문에 필요한 정보를 입력해 주세요.")
  public ResponseEntity<UserResponseDto> createUser(@RequestBody @Parameter(description = "정보를 입력해주세요")
  SignUpRequestDto requestDto){
    UserResponseDto user = userService.createUser(requestDto);
    return ResponseEntity.ok(user);
  }

  @PostMapping("/login")
  @Operation(summary = "로그인", description = "로그인입니다. 요청 본문에 필요한 정보를 입력해 주세요.")
  public ResponseEntity<Map<String, String>> login(@RequestBody @Valid @Parameter(description = "정보를 입력해주세요")
  LogInRequestDto logInRequestDto) {
    // 로그인 로직
    String token = authService.login(logInRequestDto);

    // 응답 헤더에 토큰 추가
    String bearerToken = "Bearer " + token;

    // 응답 바디에 토큰값 반환
    Map<String, String> responseData = Map.of("token", token);

    // ResponseEntity로 반환
    return ResponseEntity.ok(responseData);
  }

  @PatchMapping("/admin/{userId}/roles")
  public ResponseEntity<UserResponseDto> grantAdminRole(@PathVariable UUID userId) {
    UserResponseDto response = userService.grantAdminRole(userId);
    return ResponseEntity.ok(response);
  }

}
