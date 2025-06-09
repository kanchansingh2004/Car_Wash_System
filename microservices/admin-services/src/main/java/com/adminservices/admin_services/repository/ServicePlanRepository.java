package com.adminservices.admin_services.repository;

import com.adminservices.admin_services.entity.ServicePlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServicePlanRepository extends JpaRepository<ServicePlanEntity, Long> {
    List<ServicePlanEntity> findAllByActiveStatus(boolean activeStatus);
    Optional<ServicePlanEntity> findByServiceId(String serviceId);
    List<ServicePlanEntity> findAllByName(String name);
}

