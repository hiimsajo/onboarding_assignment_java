package com.example.onboarding_assignment.infrastructure.details;

import com.example.onboarding_assignment.domain.model.User;
import com.example.onboarding_assignment.domain.model.UserRole;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

  private final User user;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    UserRole userRole = user.getUserRole();

    // Role에서 권한문자열 가져오기
    String authority = userRole.getAuthority();

    // 권한 생성
    SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(simpleGrantedAuthority);

    return authorities;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  public UUID getUserId() {
    return user.getUserId();
  }

  public String getRole() {
    return user.getUserRole().toString(); // Role의 toString 호출
  }
}
