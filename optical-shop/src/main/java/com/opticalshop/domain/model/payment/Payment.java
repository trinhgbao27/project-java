package com.opticalshop.domain.model.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Payment {
    private final UUID id;
    private final UUID orderId;
    private final PaymentMethod method;
    private PaymentStatus status;
    private final BigDecimal amount;
    private String transactionRef;
    private LocalDateTime paidAt;

    public Payment(UUID orderId, PaymentMethod method, BigDecimal amount) {
        this.id = UUID.randomUUID(); this.orderId = orderId;
        this.method = method; this.amount = amount; this.status = PaymentStatus.PENDING;
    }

    public void markPaid(String transactionRef) {
        this.status = PaymentStatus.PAID;
        this.transactionRef = transactionRef; this.paidAt = LocalDateTime.now();
    }

    public void markFailed() { this.status = PaymentStatus.FAILED; }
    public void markRefunded() { this.status = PaymentStatus.REFUNDED; }

    public UUID getId() { return id; }
    public UUID getOrderId() { return orderId; }
    public PaymentMethod getMethod() { return method; }
    public PaymentStatus getStatus() { return status; }
    public BigDecimal getAmount() { return amount; }
    public String getTransactionRef() { return transactionRef; }
    public LocalDateTime getPaidAt() { return paidAt; }
}
