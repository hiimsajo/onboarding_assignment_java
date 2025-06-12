package com.example.onboarding_assignment.infrastructure.jwt;

import com.example.onboarding_assignment.infrastructure.details.CustomUserDetails;
import com.example.onboarding_assignment.infrastructure.details.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  private final CustomUserDetailsService customUserDetailsService;
  private final JwtTokenizer jwtTokenizer;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    // JWT 추출 및 검증
    String token = jwtTokenizer.resolveToken(request);
    if (token == null || !jwtTokenizer.validateToken(token)) {
      filterChain.doFilter(request, response);
      return;
    }

    // JWT에서 사용자 정보 추출
    String username = jwtTokenizer.getUsernameFromToken(token);
    String role = jwtTokenizer.getUserRoleFromToken(token);

    // 사용자 정보를 customUserDetails에 로드
    CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(username);
    if (!role.equals(userDetails.getRole())) {
      log.warn("Role 불일치: 기대한 role {}, 실제 role {}", userDetails.getRole(), role);
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      response.getWriter().write("{\"code\": 403, \"message\": \"Role 정보가 일치하지 않습니다\"}");
      return;
    }

    // securityContextHolder에 저장
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        userDetails, null, userDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);

    filterChain.doFilter(request, response);
  }
}
