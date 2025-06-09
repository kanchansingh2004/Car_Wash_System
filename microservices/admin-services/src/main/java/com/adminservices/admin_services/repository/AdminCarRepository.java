package com.adminservices.admin_services.repository;

import com.adminservices.admin_services.entity.AdminCarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminCarRepository extends JpaRepository<AdminCarEntity, Long> {
    Optional<AdminCarEntity> findByCarId(String carId);
}
