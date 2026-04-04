package com.opticalshop.adapter.external.payment;

import com.opticalshop.application.port.PaymentGatewayPort;
import com.opticalshop.domain.model.payment.PaymentMethod;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Router: dựa vào PaymentMethod mà delegate sang đúng adapter.
 * Use case chỉ cần inject PaymentGatewayPort, không biết Momo hay VNPay.
 */
@Component
@Primary
public class PaymentGatewayRouter implements PaymentGatewayPort {

    private final PaymentGatewayPort momoAdapter;
    private final PaymentGatewayPort vnpayAdapter;

    public PaymentGatewayRouter(
            @Qualifier("momoPaymentAdapter") PaymentGatewayPort momoAdapter,
            @Qualifier("vnpayPaymentAdapter") PaymentGatewayPort vnpayAdapter) {
        this.momoAdapter = momoAdapter;
        this.vnpayAdapter = vnpayAdapter;
    }

    @Override
    public PaymentGatewayPort.PaymentResult requestPayment(UUID orderId, BigDecimal amount, PaymentMethod method) {
        return switch (method) {
            case MOMO          -> momoAdapter.requestPayment(orderId, amount, method);
            case VNPAY         -> vnpayAdapter.requestPayment(orderId, amount, method);
            // COD, BANK_TRANSFER, CREDIT_CARD → không cần gọi gateway, tự success
            case COD           -> new PaymentGatewayPort.PaymentResult(true, "COD-" + orderId, null);
            case BANK_TRANSFER -> new PaymentGatewayPort.PaymentResult(true, "BANK-" + orderId, null);
            case CREDIT_CARD   -> new PaymentGatewayPort.PaymentResult(true, "CC-" + orderId, null);
        };
    }
}
