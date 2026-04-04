package com.opticalshop.application.usecase.prescription;

import com.opticalshop.application.port.NotificationPort;
import com.opticalshop.application.port.PaymentGatewayPort;
import com.opticalshop.domain.exception.DomainException;
import com.opticalshop.domain.model.order.*;
import com.opticalshop.domain.model.payment.Payment;
import com.opticalshop.domain.model.prescription.Prescription;
import com.opticalshop.domain.model.product.ProductVariant;
import com.opticalshop.domain.repository.*;
import com.opticalshop.domain.service.PricingService;

import java.math.BigDecimal;

public class CreatePrescriptionOrderUseCase {

    private final OrderRepository orderRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final ProductRepository productRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentGatewayPort paymentGateway;
    private final NotificationPort notificationPort;
    private final PricingService pricingService;

    public CreatePrescriptionOrderUseCase(OrderRepository orderRepository,
                                           PrescriptionRepository prescriptionRepository,
                                           ProductRepository productRepository,
                                           PaymentRepository paymentRepository,
                                           PaymentGatewayPort paymentGateway,
                                           NotificationPort notificationPort,
                                           PricingService pricingService) {
        this.orderRepository = orderRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.productRepository = productRepository;
        this.paymentRepository = paymentRepository;
        this.paymentGateway = paymentGateway;
        this.notificationPort = notificationPort;
        this.pricingService = pricingService;
    }

    public CreatePrescriptionOrderResult execute(CreatePrescriptionOrderCommand command) {
        // 1. Lấy hoặc tạo prescription
        Prescription prescription;
        if (command.existingPrescriptionId() != null) {
            prescription = prescriptionRepository.findById(command.existingPrescriptionId())
                    .orElseThrow(() -> new DomainException("Prescription not found"));
            if (!prescription.getCustomerId().equals(command.customerId()))
                throw new DomainException("Prescription does not belong to this customer");
        } else {
            CreatePrescriptionOrderCommand.PrescriptionData pd = command.newPrescription();
            prescription = Prescription.create(
                    command.customerId(),
                    pd.odSphere(), pd.odCylinder(), pd.odAxis(),
                    pd.osSphere(), pd.osCylinder(), pd.osAxis(),
                    pd.pd(), pd.issuedDate(), pd.issuedBy(), pd.notes()
            );
            prescription = prescriptionRepository.save(prescription);
        }

        // 2. Tạo order loại PRESCRIPTION
        Order order = Order.create(
                command.customerId(),
                OrderType.PRESCRIPTION,
                command.shippingAddress(),
                command.notes()
        );

        // 3. Tính giá gọng + tròng
        ProductVariant frame = null;
        if (command.frameVariantId() != null) {
            frame = productRepository.findVariantById(command.frameVariantId())
                    .orElseThrow(() -> new DomainException("Frame variant not found"));
        }

        BigDecimal unitPrice = pricingService.calculateItemPrice(
                ItemType.FRAME_LENS, frame, null);

        order.addItem(new OrderItem(
                command.frameVariantId(),
                command.lensId(),
                prescription.getId(),
                ItemType.FRAME_LENS,
                1,
                unitPrice
        ));

        Order savedOrder = orderRepository.save(order);

        // 4. Thanh toán
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

        if (paymentResult.success()) payment.markPaid(paymentResult.transactionRef());
        else payment.markFailed();
        paymentRepository.save(payment);

        // 5. Notify
        notificationPort.notifyOrderPlaced(command.customerId(), savedOrder.getOrderCode());

        return new CreatePrescriptionOrderResult(
                savedOrder.getId(),
                savedOrder.getOrderCode(),
                prescription.getId(),
                savedOrder.getStatus(),
                savedOrder.getTotalAmount(),
                "Prescription order placed. Staff will review your prescription shortly."
        );
    }
}
