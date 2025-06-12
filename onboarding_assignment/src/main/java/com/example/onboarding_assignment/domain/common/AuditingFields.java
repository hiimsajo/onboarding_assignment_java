package com.example.onboarding_assignment.domain.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditingFields {

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @Comment("생성일")
  private LocalDateTime createdAt;


  @CreatedBy
  @Column(name = "created_by", nullable = false, updatable = false)
  @Comment("생성자")
  private String createdBy;


  @LastModifiedDate
  @Column(name = "updated_at")
  @Temporal(TemporalType.TIMESTAMP)
  @Comment("수정일")
  private LocalDateTime updatedAt;


  @LastModifiedBy
  @Column(name = "updated_by")
  @Comment("수정자")
  private String updatedBy;


  @Column(name = "deleted_at")
  @Temporal(TemporalType.TIMESTAMP)
  @Comment("삭제일")
  private LocalDateTime deletedAt;

  @Column(name = "deleted_by")
  @Comment("삭제자")
  private String deletedBy;

  @Column(name = "is_deleted", nullable = false)
  private boolean isDeleted = false;


  public void deleteUser() {
    this.isDeleted = true;
    this.deletedAt = LocalDateTime.now();
    this.deletedBy = deletedBy;
  }

}
