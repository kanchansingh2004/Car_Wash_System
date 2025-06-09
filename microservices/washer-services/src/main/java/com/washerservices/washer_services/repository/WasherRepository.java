package com.washerservices.washer_services.repository;

import com.washerservices.washer_services.entity.WasherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WasherRepository extends JpaRepository<WasherEntity, Long> {
    Optional<WasherEntity> findByEmail(String email);
    Optional<WasherEntity> findByUserId(String userId);
    void deleteByUserId(String userId);

}
