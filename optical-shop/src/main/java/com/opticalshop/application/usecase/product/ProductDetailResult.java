package com.opticalshop.application.usecase.product;

import com.opticalshop.domain.model.product.ProductType;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProductDetailResult(
        UUID id,
        String name,
        String slug,
        ProductType productType,
        String description,
        List<VariantResult> variants
) {
    public record VariantResult(
            UUID id,
            String sku,
            String color,
            String size,
            BigDecimal price,
            int stockQty,
            boolean available
    ) {}
}
