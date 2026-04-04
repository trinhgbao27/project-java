package com.opticalshop.domain.model.product;

import java.math.BigDecimal;
import java.util.UUID;

public class LensCatalog {
    private final UUID id;
    private String name;
    private String brand;
    private LensType lensType;
    private CoatingType coating;
    private BigDecimal basePrice;
    private boolean active;

    public LensCatalog(UUID id, String name, String brand, LensType lensType,
                       CoatingType coating, BigDecimal basePrice) {
        this.id = id; this.name = name; this.brand = brand;
        this.lensType = lensType; this.coating = coating;
        this.basePrice = basePrice; this.active = true;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public LensType getLensType() { return lensType; }
    public CoatingType getCoating() { return coating; }
    public BigDecimal getBasePrice() { return basePrice; }
    public boolean isActive() { return active; }
}
