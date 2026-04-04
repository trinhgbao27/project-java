package com.opticalshop.adapter.external.payment;

import com.opticalshop.application.port.PaymentGatewayPort;
import com.opticalshop.domain.model.payment.PaymentMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Stub adapter cho VNPay payment gateway.
 */
@Component("vnpayPaymentAdapter")
public class VnpayPaymentAdapter implements PaymentGatewayPort {

    private static final Logger log = LoggerFactory.getLogger(VnpayPaymentAdapter.class);

    @Override
    public PaymentGatewayPort.PaymentResult requestPayment(UUID orderId, BigDecimal amount, PaymentMethod method) {
        log.info("[VNPAY] Requesting payment: orderId={}, amount={}", orderId, amount);

        // TODO: Gọi VNPay API thật ở đây
        // String vnpayUrl = buildVnpayUrl(orderId, amount);
        // return new PaymentGatewayPort.PaymentResult(false, null, vnpayUrl); // redirect flow

        String transactionRef = "VNPAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        log.info("[VNPAY] Payment success: transactionRef={}", transactionRef);
        return new PaymentGatewayPort.PaymentResult(true, transactionRef, null);
    }
}
