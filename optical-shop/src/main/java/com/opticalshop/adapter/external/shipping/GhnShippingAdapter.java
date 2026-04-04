package com.opticalshop.adapter.external.shipping;

import com.opticalshop.application.port.ShippingCarrierPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Adapter cho GHN (Giao Hàng Nhanh) shipping carrier.
 * Stub: sinh tracking number giả — tích hợp GHN REST API sau.
 */
@Component
public class GhnShippingAdapter implements ShippingCarrierPort {

    private static final Logger log = LoggerFactory.getLogger(GhnShippingAdapter.class);

    @Override
    public ShippingCarrierPort.ShipmentResult createShipment(UUID orderId, String recipientName,
                                          String address, String phone) {
        log.info("[GHN] Creating shipment: orderId={}, recipient={}, address={}",
                orderId, recipientName, address);

        // TODO: Gọi GHN API thật
        // POST https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/create
        // Headers: Token, ShopId
        // Body: { to_name, to_address, to_phone, weight, ... }

        String trackingNumber = "GHN-" + UUID.randomUUID().toString().substring(0, 10).toUpperCase();
        log.info("[GHN] Shipment created: trackingNumber={}", trackingNumber);

        return new ShippingCarrierPort.ShipmentResult(trackingNumber, "GHN");
    }
}
