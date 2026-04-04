package com.opticalshop.adapter.web.customer;

import com.opticalshop.adapter.web.dto.request.CreatePrescriptionOrderRequest;
import com.opticalshop.adapter.web.dto.request.PlaceOrderRequest;
import com.opticalshop.adapter.web.dto.response.ApiResponse;
import com.opticalshop.application.usecase.order.*;
import com.opticalshop.application.usecase.prescription.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final PlaceOrderUseCase placeOrderUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;
    private final TrackOrderUseCase trackOrderUseCase;
    private final CreatePrescriptionOrderUseCase createPrescriptionOrderUseCase;

    public OrderController(PlaceOrderUseCase placeOrderUseCase,
                           CancelOrderUseCase cancelOrderUseCase,
                           TrackOrderUseCase trackOrderUseCase,
                           CreatePrescriptionOrderUseCase createPrescriptionOrderUseCase) {
        this.placeOrderUseCase = placeOrderUseCase;
        this.cancelOrderUseCase = cancelOrderUseCase;
        this.trackOrderUseCase = trackOrderUseCase;
        this.createPrescriptionOrderUseCase = createPrescriptionOrderUseCase;
    }

    /**
     * POST /api/v1/orders
     * Đặt mua kính có sẵn hoặc pre-order.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<PlaceOrderResult>> placeOrder(
            @Valid @RequestBody PlaceOrderRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        UUID customerId = extractUserId(userDetails);

        PlaceOrderCommand command = new PlaceOrderCommand(
                customerId,
                request.orderType(),
                request.shippingAddress(),
                request.notes(),
                request.paymentMethod(),
                request.items().stream()
                        .map(i -> new PlaceOrderCommand.OrderItemCommand(
                                i.variantId(), i.lensId(), i.prescriptionId(),
                                i.itemType(), i.quantity()))
                        .toList()
        );

        PlaceOrderResult result = placeOrderUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(result));
    }

    /**
     * POST /api/v1/orders/prescription
     * Đặt làm kính theo đơn (prescription order).
     */
    @PostMapping("/prescription")
    public ResponseEntity<ApiResponse<CreatePrescriptionOrderResult>> createPrescriptionOrder(
            @Valid @RequestBody CreatePrescriptionOrderRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        UUID customerId = extractUserId(userDetails);

        CreatePrescriptionOrderCommand.PrescriptionData pd = null;
        if (request.newPrescription() != null) {
            var np = request.newPrescription();
            pd = new CreatePrescriptionOrderCommand.PrescriptionData(
                    np.odSphere(), np.odCylinder(), np.odAxis(),
                    np.osSphere(), np.osCylinder(), np.osAxis(),
                    np.pd(), np.issuedDate(), np.issuedBy(), np.notes()
            );
        }

        CreatePrescriptionOrderCommand command = new CreatePrescriptionOrderCommand(
                customerId,
                request.shippingAddress(),
                request.notes(),
                request.paymentMethod(),
                request.existingPrescriptionId(),
                pd,
                request.frameVariantId(),
                request.lensId()
        );

        CreatePrescriptionOrderResult result = createPrescriptionOrderUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(result));
    }

    /**
     * GET /api/v1/orders/{orderId}
     * Theo dõi trạng thái đơn hàng.
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<TrackOrderResult>> trackOrder(
            @PathVariable UUID orderId,
            @AuthenticationPrincipal UserDetails userDetails) {

        UUID customerId = extractUserId(userDetails);
        TrackOrderResult result = trackOrderUseCase.execute(
                new TrackOrderQuery(orderId, customerId));

        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    /**
     * DELETE /api/v1/orders/{orderId}
     * Huỷ đơn hàng.
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse<Void>> cancelOrder(
            @PathVariable UUID orderId,
            @AuthenticationPrincipal UserDetails userDetails) {

        UUID customerId = extractUserId(userDetails);
        cancelOrderUseCase.execute(new CancelOrderCommand(orderId, customerId));

        return ResponseEntity.ok(ApiResponse.ok(null, "Order cancelled successfully"));
    }

    // ──────────────────────────────────────────────
    // Helper: lấy customerId từ JWT principal
    // ──────────────────────────────────────────────

    private UUID extractUserId(UserDetails userDetails) {
        // Trong thực tế, UserDetails của mày có thể implement thêm interface
        // để lộ ra UUID. Hiện tại cast sang String rồi parse.
        // Infrastructure layer sẽ cung cấp CustomUserDetails có getId().
        return UUID.fromString(userDetails.getUsername());
    }
}
