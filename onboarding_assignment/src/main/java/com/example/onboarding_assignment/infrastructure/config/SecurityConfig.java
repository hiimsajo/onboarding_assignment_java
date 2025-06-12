package com.example.onboarding_assignment.infrastructure.config;

import com.example.onboarding_assignment.domain.repository.UserRepository;
import com.example.onboarding_assignment.infrastructure.details.CustomAuthenticationProvider;
import com.example.onboarding_assignment.infrastructure.details.CustomUserDetailsService;
import com.example.onboarding_assignment.infrastructure.jwt.JwtAuthFilter;
import com.example.onboarding_assignment.infrastructure.jwt.JwtTokenizer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtTokenizer jwtTokenizer;
  private final UserRepository userRepository;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // CustomUserDetailsService를 Bean으로 등록
  @Bean
  public CustomUserDetailsService customUserDetailsService() {
    return new CustomUserDetailsService(userRepository);
  }

  @Bean
  public AuthenticationProvider customAuthenticationProvider() {
    // CustomAuthenticationProvider 생성 및 반환
    return new CustomAuthenticationProvider(customUserDetailsService(), passwordEncoder());
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return new ProviderManager(customAuthenticationProvider()); // CustomAuthenticationProvider 등록
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    JwtAuthFilter jwtAuthorizationFilter = new JwtAuthFilter(customUserDetailsService(), jwtTokenizer);

    return http
        .csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화
        .httpBasic(AbstractHttpConfigurer::disable) // HTTP Basic 인증 비활성화
        .formLogin(AbstractHttpConfigurer::disable) // 폼 로그인 비활성화
        .sessionManagement(sm -> sm.sessionCreationPolicy(
            SessionCreationPolicy.STATELESS)) // 세션 비활성화
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/h2-console/**","/users/signup", "/users/login", "/swagger/**", "/swagger-ui.html", "/swagger-ui/**").permitAll() // 로그인 URL 허용
            .requestMatchers("/users/admin/{userId}/roles").hasAuthority("ADMIN") // 관리자 권한만 가능
            .requestMatchers("/users/admin/{userId}/roles").hasAuthority("ROLE_ADMIN")
            .anyRequest().authenticated() // 나머지는 인증 필요
        )
        .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
        .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class) // JwtAuthorizationFilter 추가
        .build();
  }

}
