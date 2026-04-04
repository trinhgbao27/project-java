package com.opticalshop.domain.model.product;

import com.opticalshop.domain.exception.DomainException;
import com.opticalshop.domain.exception.InsufficientStockException;
import java.math.BigDecimal;
import java.util.UUID;

public class ProductVariant {
    private final UUID id;
    private final UUID productId;
    private String sku;
    private String color;
    private String size;
    private BigDecimal price;
    private int stockQty;
    private boolean available;

    public ProductVariant(UUID id, UUID productId, String sku, String color,
                          String size, BigDecimal price, int stockQty) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0)
            throw new DomainException("Price must be non-negative");
        if (stockQty < 0) throw new DomainException("Stock quantity cannot be negative");
        this.id = id; this.productId = productId; this.sku = sku;
        this.color = color; this.size = size; this.price = price;
        this.stockQty = stockQty; this.available = true;
    }

    public void deductStock(int qty) {
        if (qty <= 0) throw new DomainException("Quantity must be positive");
        if (this.stockQty < qty) throw new InsufficientStockException(sku, qty, stockQty);
        this.stockQty -= qty;
        if (this.stockQty == 0) this.available = false;
    }

    public void addStock(int qty) {
        if (qty <= 0) throw new DomainException("Quantity must be positive");
        this.stockQty += qty; this.available = true;
    }

    public boolean hasStock(int qty) { return this.stockQty >= qty; }

    public UUID getId() { return id; }
    public UUID getProductId() { return productId; }
    public String getSku() { return sku; }
    public String getColor() { return color; }
    public String getSize() { return size; }
    public BigDecimal getPrice() { return price; }
    public int getStockQty() { return stockQty; }
    public boolean isAvailable() { return available; }
}
