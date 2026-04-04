package com.opticalshop.adapter.web.dto.request;

import com.opticalshop.domain.model.order.ItemType;
import com.opticalshop.domain.model.order.OrderType;
import com.opticalshop.domain.model.payment.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record PlaceOrderRequest(

        @NotNull(message = "orderType is required")
        OrderType orderType,

        @NotNull(message = "shippingAddress is required")
        String shippingAddress,

        String notes,

        @NotNull(message = "paymentMethod is required")
        PaymentMethod paymentMethod,

        @NotEmpty(message = "items must not be empty")
        @Valid
        List<OrderItemRequest> items

) {
    public record OrderItemRequest(
            UUID variantId,
            UUID lensId,
            UUID prescriptionId,

            @NotNull(message = "itemType is required")
            ItemType itemType,

            @Min(value = 1, message = "quantity must be at least 1")
            int quantity
    ) {}
}
