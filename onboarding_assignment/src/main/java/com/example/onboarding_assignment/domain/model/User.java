package com.example.onboarding_assignment.domain.model;

import static com.example.onboarding_assignment.domain.model.UserRole.USER;

import com.example.onboarding_assignment.domain.common.AuditingFields;
import com.example.onboarding_assignment.presentation.dto.requestDto.SignUpRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "oa_db_user")
@Entity
public class User extends AuditingFields {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "user_id", updatable = false, nullable = false)
  private UUID userId;

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String nickname;

  @Column(nullable = false)
  private String password;

  @Builder.Default
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private UserRole userRole = USER;

  public static User from(SignUpRequestDto requestDto, String encodedPassword) {
    return User.builder()
        .username(requestDto.getUsername())
        .nickname(requestDto.getNickname())
        .password(encodedPassword)
        .userRole(USER)
        .build();
  }

}
