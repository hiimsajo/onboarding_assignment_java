package com.example.onboarding_assignment.infrastructure.config;

import com.example.onboarding_assignment.infrastructure.details.CustomUserDetails;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
      CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
      return Optional.of(customUserDetails.getUsername());
    }

    return Optional.of("SYSTEM");
  }

}
