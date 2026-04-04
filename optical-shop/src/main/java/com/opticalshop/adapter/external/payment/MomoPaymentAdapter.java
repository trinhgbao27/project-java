package com.opticalshop.adapter.external.payment;

import com.opticalshop.application.port.PaymentGatewayPort;
import com.opticalshop.domain.model.payment.PaymentMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Stub adapter cho Momo payment gateway.
 * Trong thực tế sẽ gọi Momo API (HMAC-SHA256 signature, v.v.)
 * Hiện tại trả về success để dev/test không cần tích hợp thật.
 */
@Component("momoPaymentAdapter")
public class MomoPaymentAdapter implements PaymentGatewayPort {

    private static final Logger log = LoggerFactory.getLogger(MomoPaymentAdapter.class);

    @Override
    public PaymentGatewayPort.PaymentResult requestPayment(UUID orderId, BigDecimal amount, PaymentMethod method) {
        log.info("[MOMO] Requesting payment: orderId={}, amount={}", orderId, amount);

        // TODO: Gọi Momo API thật ở đây
        // String requestBody = buildMomoRequest(orderId, amount);
        // HttpResponse response = httpClient.post(momoEndpoint, requestBody);
        // return parseMomoResponse(response);

        // Stub: luôn thành công trong môi trường dev
        String transactionRef = "MOMO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        log.info("[MOMO] Payment success: transactionRef={}", transactionRef);
        return new PaymentGatewayPort.PaymentResult(true, transactionRef, null);
    }
}
