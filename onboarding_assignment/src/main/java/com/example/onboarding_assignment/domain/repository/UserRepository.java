package com.example.onboarding_assignment.domain.repository;

import com.example.onboarding_assignment.domain.model.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

}
