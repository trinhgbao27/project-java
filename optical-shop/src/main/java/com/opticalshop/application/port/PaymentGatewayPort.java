package com.opticalshop.application.port;

import com.opticalshop.domain.model.payment.PaymentMethod;
import java.math.BigDecimal;
import java.util.UUID;

public interface PaymentGatewayPort {
    PaymentResult requestPayment(UUID orderId, BigDecimal amount, PaymentMethod method);

    record PaymentResult(boolean success, String transactionRef, String errorMessage) {}
}
