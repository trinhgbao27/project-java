package com.opticalshop.adapter.web.dto.request;

import com.opticalshop.domain.model.payment.PaymentMethod;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CreatePrescriptionOrderRequest(

        @NotNull
        String shippingAddress,

        String notes,

        @NotNull
        PaymentMethod paymentMethod,

        // Dùng đơn kính đã lưu (chọn 1 trong 2)
        UUID existingPrescriptionId,

        // Hoặc nhập đơn kính mới
        PrescriptionDataRequest newPrescription,

        UUID frameVariantId,
        UUID lensId

) {
    public record PrescriptionDataRequest(
            BigDecimal odSphere,
            BigDecimal odCylinder,
            int odAxis,
            BigDecimal osSphere,
            BigDecimal osCylinder,
            int osAxis,
            BigDecimal pd,
            LocalDate issuedDate,
            String issuedBy,
            String notes
    ) {}
}
