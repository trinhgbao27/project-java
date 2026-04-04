package com.opticalshop.domain.repository;

import com.opticalshop.domain.model.order.Order;
import com.opticalshop.domain.model.order.OrderStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(UUID id);
    Optional<Order> findByOrderCode(String orderCode);
    List<Order> findByCustomerId(UUID customerId);
    List<Order> findByStatus(OrderStatus status);
}
