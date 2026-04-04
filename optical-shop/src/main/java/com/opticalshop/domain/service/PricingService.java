package com.opticalshop.domain.service;

import com.opticalshop.domain.model.order.ItemType;
import com.opticalshop.domain.model.product.LensCatalog;
import com.opticalshop.domain.model.product.ProductVariant;
import java.math.BigDecimal;

/**
 * Domain Service: tính giá cho từng loại item.
 * Logic này không thuộc về một entity cụ thể nào.
 */
public class PricingService {

    public BigDecimal calculateItemPrice(ItemType itemType, ProductVariant variant, LensCatalog lens) {
        return switch (itemType) {
            case FRAME_ONLY -> variant != null ? variant.getPrice() : BigDecimal.ZERO;
            case LENS_ONLY  -> lens != null ? lens.getBasePrice() : BigDecimal.ZERO;
            case FRAME_LENS -> {
                BigDecimal fp = variant != null ? variant.getPrice() : BigDecimal.ZERO;
                BigDecimal lp = lens != null ? lens.getBasePrice() : BigDecimal.ZERO;
                yield fp.add(lp);
            }
        };
    }
}
