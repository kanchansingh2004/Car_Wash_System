package com.paymentservices.payment_service.repository;

import com.paymentservices.payment_service.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    List<PaymentEntity> findByOrderId(String orderId);
    List<PaymentEntity> findByCustomerId(String customerId);
}
