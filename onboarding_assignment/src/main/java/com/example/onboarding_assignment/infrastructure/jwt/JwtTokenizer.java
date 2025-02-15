package com.example.onboarding_assignment.infrastructure.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class JwtTokenizer {

  // SecretKey를 Base64 기반으로 인코딩하기
  public String encodeSecretKey(String secretKey) {
    return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
  }

  // accessToken 발급하기
  public String generateAccessToken(Map<String, Object> claims,
      String subject,
      Date expiration,
      String base64EncodedSecretKey) {
    Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey); // Base64형식 Secret Key 문자열을 이용해 Key객체를 얻음

    return Jwts.builder()
        .setClaims(claims)          // JWT에 포함 시킬 Custom Claims를 추가함 (Custom Claims에는 주로 인증된 사용자와 관련된 정보를 추가)
        .setSubject(subject)        // JWT에 대한 제목을 추가
        .setIssuedAt(Calendar.getInstance().getTime())   // JWT 발행 일자를 설정, 파라미터 타입은 java.util.Date 타입
        .setExpiration(expiration)  // JWT의 만료일시를 지정
        .signWith(key)              // 서명을 위한 Key 객체를 설정
        .compact();                 // JWT를 생성하고 직렬화함
  }

  // accessToken이 만료되면 새로 발급해줄 refreshToken 생성하기
  public String generateRefreshToken(String subject, Date expiration, String base64EncodedSecretKey) {
    Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

    return Jwts.builder()
        .setSubject(subject)
        .setIssuedAt(Calendar.getInstance().getTime())
        .setExpiration(expiration)
        .signWith(key)
        .compact();
  }

  // JWT의 서명에 사용할 Secret Key를 생성하기
  private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
    byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);  // Base64 형식으로 인코딩된 Secret Key를 디코딩한 후, byte array를 반환
    Key key = Keys.hmacShaKeyFor(keyBytes);    // key byte array를 기반으로 적절한 HMAC 알고리즘을 적용한 Key 객체를 생성

    return key;
  }

  // 토큰 검증하기
  public void verifySignature(String jws, String base64EncodedSecretKey) {
    Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

    Jwts.parserBuilder()
        .setSigningKey(key) // 서명에 사용된 Secret Key를 설정
        .build()
        .parseClaimsJws(jws); // JWT를 파싱해서 Claims를 얻음
  }


}
