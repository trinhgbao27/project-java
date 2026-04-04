package com.opticalshop.domain.model.product;

import java.time.LocalDateTime;
import java.util.*;

public class Product {
    private final UUID id;
    private String name;
    private String slug;
    private int categoryId;
    private ProductType productType;
    private String description;
    private boolean active;
    private final LocalDateTime createdAt;
    private final List<ProductVariant> variants;

    public Product(UUID id, String name, String slug, int categoryId,
                   ProductType productType, String description) {
        this.id = id; this.name = name; this.slug = slug;
        this.categoryId = categoryId; this.productType = productType;
        this.description = description; this.active = true;
        this.createdAt = LocalDateTime.now(); this.variants = new ArrayList<>();
    }

    public static Product create(String name, String slug, int categoryId,
                                  ProductType type, String description) {
        return new Product(UUID.randomUUID(), name, slug, categoryId, type, description);
    }

    public void addVariant(ProductVariant v) { this.variants.add(v); }
    public void deactivate() { this.active = false; }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getSlug() { return slug; }
    public int getCategoryId() { return categoryId; }
    public ProductType getProductType() { return productType; }
    public String getDescription() { return description; }
    public boolean isActive() { return active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<ProductVariant> getVariants() { return Collections.unmodifiableList(variants); }
}
