package com.opticalshop.domain.exception;
public class InsufficientStockException extends DomainException {
    public InsufficientStockException(String sku, int requested, int available) {
        super(String.format("Insufficient stock for SKU %s: requested %d, available %d", sku, requested, available));
    }
}
