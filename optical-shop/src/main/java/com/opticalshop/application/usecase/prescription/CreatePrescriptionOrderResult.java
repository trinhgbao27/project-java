package com.opticalshop.application.usecase.prescription;

import com.opticalshop.domain.model.order.OrderStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record CreatePrescriptionOrderResult(
        UUID orderId,
        String orderCode,
        UUID prescriptionId,
        OrderStatus status,
        BigDecimal totalAmount,
        String message
) {}
