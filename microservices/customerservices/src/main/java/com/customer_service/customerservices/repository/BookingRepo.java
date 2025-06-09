package com.customer_service.customerservices.repository;

import com.customer_service.customerservices.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepo extends JpaRepository<BookingEntity, Long> {
    List<BookingEntity> findByUserId(String userId);
    Optional<BookingEntity> findByUserBookingId(String userBookingId);
}
