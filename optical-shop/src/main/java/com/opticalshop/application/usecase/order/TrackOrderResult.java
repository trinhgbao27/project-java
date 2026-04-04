package com.opticalshop.application.usecase.order;

import com.opticalshop.domain.model.order.OrderStatus;
import com.opticalshop.domain.model.order.OrderType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TrackOrderResult(
        UUID orderId,
        String orderCode,
        OrderType orderType,
        OrderStatus status,
        BigDecimal totalAmount,
        String shippingAddress,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
