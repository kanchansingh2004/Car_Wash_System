package com.customer_service.customerservices.repository;
import com.customer_service.customerservices.entity.CarDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarRepo extends JpaRepository<CarDetails, Long> {
    Optional<CarDetails> findByCarNumber(String carNumber);
    void deleteByCarNumber(String carNumber);
    Optional<List<CarDetails>> findAllByUserId(String userId);
}
