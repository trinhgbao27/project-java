package com.opticalshop.application.usecase.order;

import com.opticalshop.application.port.NotificationPort;
import com.opticalshop.domain.exception.DomainException;
import com.opticalshop.domain.exception.OrderNotFoundException;
import com.opticalshop.domain.model.order.Order;
import com.opticalshop.domain.repository.OrderRepository;

public class CancelOrderUseCase {

    private final OrderRepository orderRepository;
    private final NotificationPort notificationPort;

    public CancelOrderUseCase(OrderRepository orderRepository,
                               NotificationPort notificationPort) {
        this.orderRepository = orderRepository;
        this.notificationPort = notificationPort;
    }

    public void execute(CancelOrderCommand command) {
        Order order = orderRepository.findById(command.orderId())
                .orElseThrow(() -> new OrderNotFoundException(command.orderId()));

        // Chỉ customer sở hữu order mới được cancel
        if (!order.getCustomerId().equals(command.requestedBy()))
            throw new DomainException("You are not allowed to cancel this order");

        order.cancel();
        orderRepository.save(order);

        notificationPort.notifyOrderStatusChanged(
                order.getCustomerId(),
                order.getOrderCode(),
                order.getStatus().name()
        );
    }
}
