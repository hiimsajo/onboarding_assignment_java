package com.example.onboarding_assignment.application.service;

import com.example.onboarding_assignment.domain.model.User;
import com.example.onboarding_assignment.domain.model.UserRole;
import com.example.onboarding_assignment.domain.repository.UserRepository;
import com.example.onboarding_assignment.presentation.dto.requestDto.SignUpRequestDto;
import com.example.onboarding_assignment.presentation.dto.responseDto.UserResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserResponseDto createUser(SignUpRequestDto reqDto) {
    checkUsername(reqDto.getUsername());

    String encodedPassword = passwordEncoder.encode(reqDto.getPassword());

    User savedUser = userRepository.save(User.from(reqDto, encodedPassword));
    savedUser.setCreatedBy(reqDto.getUsername());
    savedUser = userRepository.save(savedUser);

    return UserResponseDto.from(savedUser);
  }

  @PreAuthorize("hasRole('ADMIN')")
  public UserResponseDto grantAdminRole(UUID userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));
    user.setUserRole(UserRole.ADMIN); // setter 필요
    userRepository.save(user);
    return UserResponseDto.from(user);
  }

  private void checkUsername(String username) {
    if (userRepository.existsByUsernameAndIsDeletedFalse(username)) {
      throw new RuntimeException("이미 가입된 사용자입니다.");
    }
  }

}
