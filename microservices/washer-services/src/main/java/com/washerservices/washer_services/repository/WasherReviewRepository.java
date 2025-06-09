package com.washerservices.washer_services.repository;

import com.washerservices.washer_services.entity.WasherReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WasherReviewRepository extends JpaRepository<WasherReviewEntity, Long> {
    List<WasherReviewEntity> findByWasherId(String washerId);
}
