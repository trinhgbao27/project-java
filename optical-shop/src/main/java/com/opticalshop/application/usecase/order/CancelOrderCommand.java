package com.opticalshop.application.usecase.order;

import java.util.UUID;

public record CancelOrderCommand(UUID orderId, UUID requestedBy) {}
