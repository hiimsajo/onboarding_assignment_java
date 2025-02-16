package com.example.onboarding_assignment.presentation.controller;

import com.example.onboarding_assignment.application.service.UserService;
import com.example.onboarding_assignment.domain.model.User;
import com.example.onboarding_assignment.presentation.dto.requestDto.SignUpRequestDto;
import com.example.onboarding_assignment.presentation.dto.responseDto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<UserResponseDto> createUser(@RequestBody SignUpRequestDto requestDto){
    UserResponseDto user = userService.createUser(requestDto);
    return ResponseEntity.ok(user);
  }



}
