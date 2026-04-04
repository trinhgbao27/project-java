package com.opticalshop.application.usecase.order;

import com.opticalshop.domain.model.order.OrderStatus;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Output data của PlaceOrderUseCase trả về cho Controller.
 */
public record PlaceOrderResult(
        UUID orderId,
        String orderCode,
        OrderStatus status,
        BigDecimal totalAmount,
        String paymentTransactionRef
) {}
