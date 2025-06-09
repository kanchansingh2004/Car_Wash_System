package com.adminservices.admin_services.repository;

import com.adminservices.admin_services.entity.AdminCustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminCustomerRepository extends JpaRepository<AdminCustomerEntity, Long> {

    Optional<AdminCustomerEntity> findByCustomerId(String customerId);
}
