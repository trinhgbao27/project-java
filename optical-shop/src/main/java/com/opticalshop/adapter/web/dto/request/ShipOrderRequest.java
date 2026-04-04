package com.opticalshop.adapter.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ShipOrderRequest(
        @NotBlank String recipientName,
        @NotBlank String address,
        @NotBlank String phone
) {}
