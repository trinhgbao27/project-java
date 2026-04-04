package com.opticalshop.adapter.web.staff;

import com.opticalshop.adapter.web.dto.request.ShipOrderRequest;
import com.opticalshop.adapter.web.dto.response.ApiResponse;
import com.opticalshop.application.usecase.staff.ConfirmOrderUseCase;
import com.opticalshop.application.usecase.staff.UpdateShipmentUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/staff/orders")
public class StaffOrderController {

    private final ConfirmOrderUseCase confirmOrderUseCase;
    private final UpdateShipmentUseCase updateShipmentUseCase;

    public StaffOrderController(ConfirmOrderUseCase confirmOrderUseCase,
                                 UpdateShipmentUseCase updateShipmentUseCase) {
        this.confirmOrderUseCase = confirmOrderUseCase;
        this.updateShipmentUseCase = updateShipmentUseCase;
    }

    /**
     * POST /api/v1/staff/orders/{orderId}/confirm
     * Staff xác nhận đơn hàng → chuyển PENDING → CONFIRMED.
     */
    @PostMapping("/{orderId}/confirm")
    public ResponseEntity<ApiResponse<Void>> confirmOrder(@PathVariable UUID orderId) {
        confirmOrderUseCase.execute(orderId);
        return ResponseEntity.ok(ApiResponse.ok(null, "Order confirmed successfully"));
    }

    /**
     * POST /api/v1/staff/orders/{orderId}/ship
     * Staff tạo vận đơn + chuyển PROCESSING → SHIPPED.
     */
    @PostMapping("/{orderId}/ship")
    public ResponseEntity<ApiResponse<String>> shipOrder(
            @PathVariable UUID orderId,
            @Valid @RequestBody ShipOrderRequest request) {

        String trackingNumber = updateShipmentUseCase.execute(
                new UpdateShipmentUseCase.ShipOrderCommand(
                        orderId,
                        request.recipientName(),
                        request.address(),
                        request.phone()
                )
        );

        return ResponseEntity.ok(ApiResponse.ok(trackingNumber, "Shipment created successfully"));
    }
}
