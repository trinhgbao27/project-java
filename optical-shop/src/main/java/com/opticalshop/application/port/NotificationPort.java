package com.opticalshop.application.port;

import java.util.UUID;

public interface NotificationPort {
    void notifyOrderPlaced(UUID customerId, String orderCode);
    void notifyOrderStatusChanged(UUID customerId, String orderCode, String newStatus);
    void notifyOrderShipped(UUID customerId, String orderCode, String trackingNumber);
}
