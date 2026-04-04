package com.opticalshop.application.usecase.staff;

import com.opticalshop.application.port.NotificationPort;
import com.opticalshop.application.port.ShippingCarrierPort;
import com.opticalshop.domain.exception.OrderNotFoundException;
import com.opticalshop.domain.model.order.Order;
import com.opticalshop.domain.repository.OrderRepository;

import java.util.UUID;

public class UpdateShipmentUseCase {

    private final OrderRepository orderRepository;
    private final ShippingCarrierPort shippingCarrier;
    private final NotificationPort notificationPort;

    public UpdateShipmentUseCase(OrderRepository orderRepository,
                                  ShippingCarrierPort shippingCarrier,
                                  NotificationPort notificationPort) {
        this.orderRepository = orderRepository;
        this.shippingCarrier = shippingCarrier;
        this.notificationPort = notificationPort;
    }

    public record ShipOrderCommand(UUID orderId, String recipientName,
                                   String address, String phone) {}

    public String execute(ShipOrderCommand command) {
        Order order = orderRepository.findById(command.orderId())
                .orElseThrow(() -> new OrderNotFoundException(command.orderId()));

        // Tạo vận đơn với carrier
        ShippingCarrierPort.ShipmentResult shipment = shippingCarrier.createShipment(
                order.getId(),
                command.recipientName(),
                command.address(),
                command.phone()
        );

        order.ship();
        orderRepository.save(order);

        notificationPort.notifyOrderShipped(
                order.getCustomerId(),
                order.getOrderCode(),
                shipment.trackingNumber()
        );

        return shipment.trackingNumber();
    }
}
