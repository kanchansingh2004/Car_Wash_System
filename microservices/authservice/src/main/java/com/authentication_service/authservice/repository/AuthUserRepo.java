package com.authentication_service.authservice.repository;

import com.authentication_service.authservice.entity.AuthUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUserRepo extends JpaRepository<AuthUserEntity, Long> {
    Optional<AuthUserEntity> findByEmail(String email);
}
