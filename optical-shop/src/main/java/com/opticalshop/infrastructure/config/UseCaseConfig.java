package com.opticalshop.infrastructure.config;

import com.opticalshop.application.port.NotificationPort;
import com.opticalshop.application.port.PaymentGatewayPort;
import com.opticalshop.application.port.ShippingCarrierPort;
import com.opticalshop.application.usecase.order.*;
import com.opticalshop.application.usecase.prescription.CreatePrescriptionOrderUseCase;
import com.opticalshop.application.usecase.product.GetProductDetailUseCase;
import com.opticalshop.application.usecase.staff.ConfirmOrderUseCase;
import com.opticalshop.application.usecase.staff.UpdateShipmentUseCase;
import com.opticalshop.domain.repository.*;
import com.opticalshop.domain.service.PricingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Wiring toàn bộ Use Cases với các dependencies của chúng.
 *
 * Use Cases là plain Java objects — KHÔNG dùng @Service/@Component
 * để giữ domain/application layer độc lập với Spring.
 * Chỉ có layer Infrastructure này biết Spring.
 */
@Configuration
public class UseCaseConfig {

    // ─────────────────────────────────────────────
    // Domain Services
    // ─────────────────────────────────────────────

    @Bean
    public PricingService pricingService() {
        return new PricingService();
    }

    // ─────────────────────────────────────────────
    // Order Use Cases
    // ─────────────────────────────────────────────

    @Bean
    public PlaceOrderUseCase placeOrderUseCase(
            OrderRepository orderRepository,
            ProductRepository productRepository,
            PaymentRepository paymentRepository,
            PaymentGatewayPort paymentGateway,
            NotificationPort notificationPort,
            PricingService pricingService) {
        return new PlaceOrderUseCase(
                orderRepository, productRepository, paymentRepository,
                paymentGateway, notificationPort, pricingService);
    }

    @Bean
    public CancelOrderUseCase cancelOrderUseCase(
            OrderRepository orderRepository,
            NotificationPort notificationPort) {
        return new CancelOrderUseCase(orderRepository, notificationPort);
    }

    @Bean
    public TrackOrderUseCase trackOrderUseCase(OrderRepository orderRepository) {
        return new TrackOrderUseCase(orderRepository);
    }

    // ─────────────────────────────────────────────
    // Prescription Use Case
    // ─────────────────────────────────────────────

    @Bean
    public CreatePrescriptionOrderUseCase createPrescriptionOrderUseCase(
            OrderRepository orderRepository,
            PrescriptionRepository prescriptionRepository,
            ProductRepository productRepository,
            PaymentRepository paymentRepository,
            PaymentGatewayPort paymentGateway,
            NotificationPort notificationPort,
            PricingService pricingService) {
        return new CreatePrescriptionOrderUseCase(
                orderRepository, prescriptionRepository, productRepository,
                paymentRepository, paymentGateway, notificationPort, pricingService);
    }

    // ─────────────────────────────────────────────
    // Product Use Case
    // ─────────────────────────────────────────────

    @Bean
    public GetProductDetailUseCase getProductDetailUseCase(ProductRepository productRepository) {
        return new GetProductDetailUseCase(productRepository);
    }

    // ─────────────────────────────────────────────
    // Staff Use Cases
    // ─────────────────────────────────────────────

    @Bean
    public ConfirmOrderUseCase confirmOrderUseCase(
            OrderRepository orderRepository,
            NotificationPort notificationPort) {
        return new ConfirmOrderUseCase(orderRepository, notificationPort);
    }

    @Bean
    public UpdateShipmentUseCase updateShipmentUseCase(
            OrderRepository orderRepository,
            ShippingCarrierPort shippingCarrier,
            NotificationPort notificationPort) {
        return new UpdateShipmentUseCase(orderRepository, shippingCarrier, notificationPort);
    }
}
