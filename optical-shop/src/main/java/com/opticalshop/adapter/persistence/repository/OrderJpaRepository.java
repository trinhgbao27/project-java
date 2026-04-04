package com.opticalshop.adapter.persistence.repository;

import com.opticalshop.adapter.persistence.entity.OrderJpaEntity;
import com.opticalshop.domain.model.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderJpaEntity, UUID> {
    Optional<OrderJpaEntity> findByOrderCode(String orderCode);
    List<OrderJpaEntity> findByCustomerId(UUID customerId);
    List<OrderJpaEntity> findByStatus(OrderStatus status);
}
