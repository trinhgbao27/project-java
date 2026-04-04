package com.opticalshop.adapter.web.dto.response;

import com.opticalshop.domain.model.order.OrderStatus;
import com.opticalshop.domain.model.order.OrderType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderResponse(
        UUID orderId,
        String orderCode,
        OrderType orderType,
        OrderStatus status,
        BigDecimal totalAmount,
        String shippingAddress,
        String paymentTransactionRef,
        LocalDateTime createdAt
) {}
