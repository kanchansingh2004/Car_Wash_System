package com.washerservices.washer_services.repository;

import com.washerservices.washer_services.dto.WashRequestDTO;
import com.washerservices.washer_services.entity.WashRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PendingRequestRepository extends JpaRepository<WashRequestEntity, Long> {
    WashRequestEntity findByUserBookingId(String userBookingId);
    List<WashRequestEntity> findAllByWasherId(String washerId);
}
