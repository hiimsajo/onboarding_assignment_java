package com.example.onboarding_assignment.infrastructure.jwt;

import com.example.onboarding_assignment.domain.model.UserRole;
import com.example.onboarding_assignment.presentation.dto.responseDto.AuthorityDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenizer {

  @Value("${service.jwt.access-expiration}")
  private Long accessExpiration;

  public static final String AUTHORIZATION_ROLE = "userRole";

  private final SecretKey secretKey;

  public JwtTokenizer(@Value("${service.jwt.secret-key}") String secretKey) {
    this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
  }

  // accessToken 발급하기
  public String generateAccessToken(String userRole,
      String username) {
    return Jwts.builder()
        .claim(AUTHORIZATION_ROLE, "ROLE_" + userRole)          // JWT에 포함 시킬 Claims(role)
        .setSubject(username)        // JWT에 대한 제목을 추가
        .setIssuedAt(Calendar.getInstance().getTime())   // JWT 발행 일자를 설정, 파라미터 타입은 java.util.Date 타입
        .setExpiration(new Date(System.currentTimeMillis() + accessExpiration))  // JWT의 만료일시를 지정
        .signWith(secretKey)              // 서명을 위한 Key 객체를 설정
        .compact();                 // JWT를 생성하고 직렬화함
  }

  // accessToken이 만료되면 새로 발급해줄 refreshToken 생성하기
  public String generateRefreshToken(String subject, Date expiration) {
    return Jwts.builder()
        .setSubject(subject)
        .setIssuedAt(Calendar.getInstance().getTime())
        .setExpiration(expiration)
        .signWith(secretKey)
        .compact();
  }


  public boolean validateToken(String token) {
    try {
      // 서명 검증
      verifySignature(token);

      // JWT 파싱을 통해 클레임을 추출하려고 시도
      Jwts.parserBuilder()
          .build()
          .parseClaimsJws(token);
      return true;
    } catch (ExpiredJwtException e) {
      log.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("JWT token is unsupported: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      log.error("JWT token is malformed: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("JWT token is invalid: {}", e.getMessage());
    } catch (SignatureException e) {
      log.error("JWT token signature is invalid: {}", e.getMessage());
    }
    return false;
  }

  // 토큰 서명 검증하기
  public void verifySignature(String jws) {
    Jwts.parserBuilder()
        .setSigningKey(secretKey) // 서명에 사용된 Secret Key를 설정
        .build()
        .parseClaimsJws(jws); // JWT를 파싱해서 Claims를 얻음
  }

  // 토큰 추출하기
  public String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7); // "Bearer " 이후의 토큰 값
    }
    return null;
  }

  // 클레임을 추출하는 메서드
  public Claims getClaimsFromToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  // 사용자 이름 추출하기
  public String getUsernameFromToken(String token) {
    Claims claims = getClaimsFromToken(token);
    return claims.getSubject(); // subject가 username
  }

  // 사용자 역할 추출하기
  public String getUserRoleFromToken(String token) {
    Claims claims = getClaimsFromToken(token);
    return claims.get(AUTHORIZATION_ROLE, String.class); // "role" 클레임을 추출
  }


}
