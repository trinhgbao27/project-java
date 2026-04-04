package com.opticalshop.domain.event;

import com.opticalshop.domain.model.order.OrderType;
import java.util.UUID;

public class OrderPlacedEvent extends DomainEvent {
    private final UUID orderId;
    private final UUID customerId;
    private final OrderType orderType;

    public OrderPlacedEvent(UUID orderId, UUID customerId, OrderType orderType) {
        super(); this.orderId = orderId; this.customerId = customerId; this.orderType = orderType;
    }

    public UUID getOrderId() { return orderId; }
    public UUID getCustomerId() { return customerId; }
    public OrderType getOrderType() { return orderType; }
}
