package com.opticalshop.application.usecase.prescription;

import com.opticalshop.domain.model.payment.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CreatePrescriptionOrderCommand(
        UUID customerId,
        String shippingAddress,
        String notes,
        PaymentMethod paymentMethod,

        // Thông tin đơn kính
        UUID existingPrescriptionId,    // nếu dùng đơn kính đã lưu
        PrescriptionData newPrescription, // nếu nhập đơn kính mới

        // Gọng + tròng
        UUID frameVariantId,
        UUID lensId
) {
    public record PrescriptionData(
            BigDecimal odSphere, BigDecimal odCylinder, int odAxis,
            BigDecimal osSphere, BigDecimal osCylinder, int osAxis,
            BigDecimal pd, LocalDate issuedDate, String issuedBy, String notes
    ) {}
}
