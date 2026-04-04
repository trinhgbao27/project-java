package com.opticalshop.domain.model.order;

import com.opticalshop.domain.exception.DomainException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Order aggregate root — chứa toàn bộ business logic của đơn hàng.
 */
public class Order {
    private final UUID id;
    private final String orderCode;
    private final UUID customerId;
    private final OrderType orderType;
    private OrderStatus status;
    private final List<OrderItem> items;
    private BigDecimal discountAmount;
    private BigDecimal shippingFee;
    private String shippingAddress;
    private String notes;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Order(UUID id, String orderCode, UUID customerId, OrderType orderType,
                  String shippingAddress, String notes) {
        this.id = id; this.orderCode = orderCode; this.customerId = customerId;
        this.orderType = orderType; this.status = OrderStatus.PENDING;
        this.items = new ArrayList<>();
        this.discountAmount = BigDecimal.ZERO; this.shippingFee = BigDecimal.ZERO;
        this.shippingAddress = shippingAddress; this.notes = notes;
        this.createdAt = LocalDateTime.now(); this.updatedAt = LocalDateTime.now();
    }

    public static Order create(UUID customerId, OrderType orderType,
                                String shippingAddress, String notes) {
        String code = "ORD-" + System.currentTimeMillis();
        return new Order(UUID.randomUUID(), code, customerId, orderType, shippingAddress, notes);
    }

    public void addItem(OrderItem item) {
        if (this.status != OrderStatus.PENDING)
            throw new DomainException("Cannot add items to a non-pending order");
        this.items.add(item);
        this.updatedAt = LocalDateTime.now();
    }

    public void applyDiscount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0)
            throw new DomainException("Discount cannot be negative");
        if (amount.compareTo(getSubtotal()) > 0)
            throw new DomainException("Discount cannot exceed subtotal");
        this.discountAmount = amount;
        this.updatedAt = LocalDateTime.now();
    }

    public void setShippingFee(BigDecimal fee) {
        if (fee.compareTo(BigDecimal.ZERO) < 0)
            throw new DomainException("Shipping fee cannot be negative");
        this.shippingFee = fee; this.updatedAt = LocalDateTime.now();
    }

    public void confirm() {
        requireStatus(OrderStatus.PENDING);
        this.status = OrderStatus.CONFIRMED; this.updatedAt = LocalDateTime.now();
    }

    public void startProcessing() {
        requireStatus(OrderStatus.CONFIRMED);
        this.status = OrderStatus.PROCESSING; this.updatedAt = LocalDateTime.now();
    }

    public void ship() {
        requireStatus(OrderStatus.PROCESSING);
        this.status = OrderStatus.SHIPPED; this.updatedAt = LocalDateTime.now();
    }

    public void deliver() {
        requireStatus(OrderStatus.SHIPPED);
        this.status = OrderStatus.DELIVERED; this.updatedAt = LocalDateTime.now();
    }

    public void cancel() {
        if (this.status == OrderStatus.SHIPPED || this.status == OrderStatus.DELIVERED)
            throw new DomainException("Cannot cancel an order that is already shipped or delivered");
        this.status = OrderStatus.CANCELLED; this.updatedAt = LocalDateTime.now();
    }

    public BigDecimal getSubtotal() {
        return items.stream().map(OrderItem::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalAmount() {
        return getSubtotal().subtract(discountAmount).add(shippingFee);
    }

    private void requireStatus(OrderStatus required) {
        if (this.status != required)
            throw new DomainException(
                    String.format("Order must be %s, but is %s", required, this.status));
    }

    public UUID getId() { return id; }
    public String getOrderCode() { return orderCode; }
    public UUID getCustomerId() { return customerId; }
    public OrderType getOrderType() { return orderType; }
    public OrderStatus getStatus() { return status; }
    public List<OrderItem> getItems() { return Collections.unmodifiableList(items); }
    public BigDecimal getDiscountAmount() { return discountAmount; }
    public BigDecimal getShippingFee() { return shippingFee; }
    public String getShippingAddress() { return shippingAddress; }
    public String getNotes() { return notes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
