package com.opticalshop.application.usecase.order;

import com.opticalshop.domain.exception.DomainException;
import com.opticalshop.domain.exception.OrderNotFoundException;
import com.opticalshop.domain.model.order.Order;
import com.opticalshop.domain.repository.OrderRepository;

public class TrackOrderUseCase {

    private final OrderRepository orderRepository;

    public TrackOrderUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public TrackOrderResult execute(TrackOrderQuery query) {
        Order order = orderRepository.findById(query.orderId())
                .orElseThrow(() -> new OrderNotFoundException(query.orderId()));

        if (!order.getCustomerId().equals(query.customerId()))
            throw new DomainException("You are not allowed to view this order");

        return new TrackOrderResult(
                order.getId(),
                order.getOrderCode(),
                order.getOrderType(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getShippingAddress(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }
}
