package com.adminservices.admin_services.repository;

import com.adminservices.admin_services.entity.AdminWasherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminWasherRepository extends JpaRepository<AdminWasherEntity, Long>{
    Optional<AdminWasherEntity> findByWasherId(String washerId);
}
