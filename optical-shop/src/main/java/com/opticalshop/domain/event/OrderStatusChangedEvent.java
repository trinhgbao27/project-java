package com.opticalshop.domain.event;

import com.opticalshop.domain.model.order.OrderStatus;
import java.util.UUID;

public class OrderStatusChangedEvent extends DomainEvent {
    private final UUID orderId;
    private final OrderStatus previousStatus;
    private final OrderStatus newStatus;

    public OrderStatusChangedEvent(UUID orderId, OrderStatus previousStatus, OrderStatus newStatus) {
        super(); this.orderId = orderId; this.previousStatus = previousStatus; this.newStatus = newStatus;
    }

    public UUID getOrderId() { return orderId; }
    public OrderStatus getPreviousStatus() { return previousStatus; }
    public OrderStatus getNewStatus() { return newStatus; }
}
