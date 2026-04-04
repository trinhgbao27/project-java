package com.opticalshop.application.usecase.staff;

import com.opticalshop.application.port.NotificationPort;
import com.opticalshop.domain.exception.OrderNotFoundException;
import com.opticalshop.domain.model.order.Order;
import com.opticalshop.domain.repository.OrderRepository;

import java.util.UUID;

public class ConfirmOrderUseCase {

    private final OrderRepository orderRepository;
    private final NotificationPort notificationPort;

    public ConfirmOrderUseCase(OrderRepository orderRepository,
                                NotificationPort notificationPort) {
        this.orderRepository = orderRepository;
        this.notificationPort = notificationPort;
    }

    public void execute(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        order.confirm();
        orderRepository.save(order);

        notificationPort.notifyOrderStatusChanged(
                order.getCustomerId(),
                order.getOrderCode(),
                order.getStatus().name()
        );
    }
}
