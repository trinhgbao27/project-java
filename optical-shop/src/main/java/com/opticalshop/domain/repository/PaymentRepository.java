package com.opticalshop.domain.repository;

import com.opticalshop.domain.model.payment.Payment;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository {
    Payment save(Payment payment);
    Optional<Payment> findByOrderId(UUID orderId);
}
