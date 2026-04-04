package com.opticalshop.domain.exception;
import java.util.UUID;
public class OrderNotFoundException extends DomainException {
    public OrderNotFoundException(UUID id) { super("Order not found: " + id); }
}
