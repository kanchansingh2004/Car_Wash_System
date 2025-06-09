package com.customer_service.customerservices.repository;

import com.customer_service.customerservices.entity.CustomerReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerReviewRepo extends JpaRepository<CustomerReviewEntity, Long> {
    List<CustomerReviewEntity> findByCustomerId(String customerId);
}
