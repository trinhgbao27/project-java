package com.opticalshop.application.port;

import java.util.UUID;

public interface ShippingCarrierPort {
    ShipmentResult createShipment(UUID orderId, String recipientName,
                                   String address, String phone);

    record ShipmentResult(String trackingNumber, String carrier) {}
}
