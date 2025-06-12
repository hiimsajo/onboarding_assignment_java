package com.example.onboarding_assignment.infrastructure.details;

import com.example.onboarding_assignment.domain.model.User;
import com.example.onboarding_assignment.domain.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsernameAndIsDeletedFalse(username)
        .orElseThrow(() -> new UsernameNotFoundException("Not Found username: " + username));

    return new CustomUserDetails(user);
  }
}
