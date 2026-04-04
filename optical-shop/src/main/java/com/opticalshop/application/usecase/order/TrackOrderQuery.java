package com.opticalshop.application.usecase.order;

import java.util.UUID;

public record TrackOrderQuery(UUID orderId, UUID customerId) {}
