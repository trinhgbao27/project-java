package com.opticalshop.adapter.persistence.repository;

import com.opticalshop.adapter.persistence.entity.PrescriptionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PrescriptionJpaRepository extends JpaRepository<PrescriptionJpaEntity, UUID> {
    List<PrescriptionJpaEntity> findByCustomerId(UUID customerId);
}
