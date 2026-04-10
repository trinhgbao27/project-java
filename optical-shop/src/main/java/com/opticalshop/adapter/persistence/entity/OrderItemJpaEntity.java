package com.opticalshop.adapter.persistence.entity;

import com.opticalshop.domain.model.order.ItemType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_items")
public class OrderItemJpaEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderJpaEntity order;

    @Column(name = "variant_id", columnDefinition = "uuid")
    private UUID variantId;

    @Column(name = "lens_id", columnDefinition = "uuid")
    private UUID lensId;

    @Column(name = "prescription_id", columnDefinition = "uuid")
    private UUID prescriptionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_type", nullable = false)
    private ItemType itemType;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "unit_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;

    public OrderItemJpaEntity() {}

    // Getters & Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public OrderJpaEntity getOrder() { return order; }
    public void setOrder(OrderJpaEntity order) { this.order = order; }
    public UUID getVariantId() { return variantId; }
    public void setVariantId(UUID variantId) { this.variantId = variantId; }
    public UUID getLensId() { return lensId; }
    public void setLensId(UUID lensId) { this.lensId = lensId; }
    public UUID getPrescriptionId() { return prescriptionId; }
    public void setPrescriptionId(UUID prescriptionId) { this.prescriptionId = prescriptionId; }
    public ItemType getItemType() { return itemType; }
    public void setItemType(ItemType itemType) { this.itemType = itemType; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}
