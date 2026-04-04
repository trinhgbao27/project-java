package com.opticalshop.application.usecase.order;

import com.opticalshop.domain.model.order.ItemType;
import com.opticalshop.domain.model.order.OrderType;
import com.opticalshop.domain.model.payment.PaymentMethod;

import java.util.List;
import java.util.UUID;

/**
 * Input data cho PlaceOrderUseCase.
 * Đây là plain Java object, không có dependency vào Spring hay JPA.
 */
public record PlaceOrderCommand(
        UUID customerId,
        OrderType orderType,
        String shippingAddress,
        String notes,
        PaymentMethod paymentMethod,
        List<OrderItemCommand> items
) {
    public record OrderItemCommand(
            UUID variantId,
            UUID lensId,
            UUID prescriptionId,
            ItemType itemType,
            int quantity
    ) {}
}
