package com.washerservices.washer_services.repository;

import com.washerservices.washer_services.entity.WasherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WasherRepository extends JpaRepository<WasherEntity, Long> {
}
