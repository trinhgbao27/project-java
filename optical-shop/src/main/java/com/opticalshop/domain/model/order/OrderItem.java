package com.opticalshop.domain.model.order;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderItem {
    private final UUID id;
    private final UUID variantId;
    private final UUID lensId;
    private final UUID prescriptionId;
    private final ItemType itemType;
    private final int quantity;
    private final BigDecimal unitPrice;
    private final BigDecimal subtotal;

    public OrderItem(UUID variantId, UUID lensId, UUID prescriptionId,
                     ItemType itemType, int quantity, BigDecimal unitPrice) {
        this.id = UUID.randomUUID();
        this.variantId = variantId; this.lensId = lensId;
        this.prescriptionId = prescriptionId; this.itemType = itemType;
        this.quantity = quantity; this.unitPrice = unitPrice;
        this.subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public UUID getId() { return id; }
    public UUID getVariantId() { return variantId; }
    public UUID getLensId() { return lensId; }
    public UUID getPrescriptionId() { return prescriptionId; }
    public ItemType getItemType() { return itemType; }
    public int getQuantity() { return quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public BigDecimal getSubtotal() { return subtotal; }
}
