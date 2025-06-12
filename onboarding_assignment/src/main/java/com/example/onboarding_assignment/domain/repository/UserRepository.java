package com.example.onboarding_assignment.domain.repository;

import com.example.onboarding_assignment.domain.model.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

  boolean existsByUsernameAndIsDeletedFalse(String username);

  Optional<User> findByUsername(String username);

  Optional<User> findByUsernameAndIsDeletedFalse(String username);
}
