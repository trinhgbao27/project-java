package com.opticalshop.application.usecase.order;

import com.opticalshop.application.port.NotificationPort;
import com.opticalshop.application.port.PaymentGatewayPort;
import com.opticalshop.domain.exception.DomainException;
import com.opticalshop.domain.model.order.Order;
import com.opticalshop.domain.model.order.OrderItem;
import com.opticalshop.domain.model.payment.Payment;
import com.opticalshop.domain.model.product.LensCatalog;
import com.opticalshop.domain.model.product.ProductVariant;
import com.opticalshop.domain.repository.OrderRepository;
import com.opticalshop.domain.repository.PaymentRepository;
import com.opticalshop.domain.repository.ProductRepository;
import com.opticalshop.domain.service.PricingService;

import java.math.BigDecimal;

public class PlaceOrderUseCase {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentGatewayPort paymentGateway;
    private final NotificationPort notificationPort;
    private final PricingService pricingService;

    public PlaceOrderUseCase(OrderRepository orderRepository,
                              ProductRepository productRepository,
                              PaymentRepository paymentRepository,
                              PaymentGatewayPort paymentGateway,
                              NotificationPort notificationPort,
                              PricingService pricingService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.paymentRepository = paymentRepository;
        this.paymentGateway = paymentGateway;
        this.notificationPort = notificationPort;
        this.pricingService = pricingService;
    }

    public PlaceOrderResult execute(PlaceOrderCommand command) {
        // 1. Tạo Order aggregate
        Order order = Order.create(
                command.customerId(),
                command.orderType(),
                command.shippingAddress(),
                command.notes()
        );

        // 2. Thêm từng item, tính giá, trừ stock
        for (PlaceOrderCommand.OrderItemCommand itemCmd : command.items()) {
            ProductVariant variant = null;
            LensCatalog lens = null;

            if (itemCmd.variantId() != null) {
                variant = productRepository.findVariantById(itemCmd.variantId())
                        .orElseThrow(() -> new DomainException("Variant not found: " + itemCmd.variantId()));
                variant.deductStock(itemCmd.quantity());
                productRepository.saveVariant(variant);
            }

            if (itemCmd.lensId() != null) {
                // LensCatalog lookup — bổ sung LensCatalogRepository sau nếu cần
                // hiện tại để null nếu chưa có
            }

            BigDecimal unitPrice = pricingService.calculateItemPrice(
                    itemCmd.itemType(), variant, lens);

            order.addItem(new OrderItem(
                    itemCmd.variantId(),
                    itemCmd.lensId(),
                    itemCmd.prescriptionId(),
                    itemCmd.itemType(),
                    itemCmd.quantity(),
                    unitPrice
            ));
        }

        // 3. Lưu order
        Order savedOrder = orderRepository.save(order);

        // 4. Xử lý thanh toán
        Payment payment = new Payment(
                savedOrder.getId(),
                command.paymentMethod(),
                savedOrder.getTotalAmount()
        );

        PaymentGatewayPort.PaymentResult paymentResult = paymentGateway.requestPayment(
                savedOrder.getId(),
                savedOrder.getTotalAmount(),
                command.paymentMethod()
        );

        if (paymentResult.success()) {
            payment.markPaid(paymentResult.transactionRef());
        } else {
            payment.markFailed();
        }

        paymentRepository.save(payment);

        // 5. Gửi notification
        notificationPort.notifyOrderPlaced(command.customerId(), savedOrder.getOrderCode());

        return new PlaceOrderResult(
                savedOrder.getId(),
                savedOrder.getOrderCode(),
                savedOrder.getStatus(),
                savedOrder.getTotalAmount(),
                paymentResult.transactionRef()
        );
    }
}
