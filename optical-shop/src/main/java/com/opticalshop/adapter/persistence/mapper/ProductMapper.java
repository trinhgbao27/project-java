package com.opticalshop.adapter.persistence.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.opticalshop.adapter.persistence.entity.ProductJpaEntity;
import com.opticalshop.adapter.persistence.entity.ProductVariantJpaEntity;
import com.opticalshop.domain.model.product.Product;
import com.opticalshop.domain.model.product.ProductVariant;

@Component
public class ProductMapper {

    // ──────────────────────────────────────────────
    // Domain → JPA
    // ──────────────────────────────────────────────

    public ProductJpaEntity toJpa(Product product) {
        ProductJpaEntity entity = new ProductJpaEntity();
        entity.setId(product.getId());
        entity.setName(product.getName());
        entity.setSlug(product.getSlug());
        entity.setCategoryId(product.getCategoryId());
        entity.setProductType(product.getProductType());
        entity.setDescription(product.getDescription());
        entity.setActive(product.isActive());
        entity.setCreatedAt(product.getCreatedAt());

        List<ProductVariantJpaEntity> variantEntities = product.getVariants().stream()
                .map(v -> toVariantJpa(v, entity))
                .toList();
        entity.setVariants(variantEntities);

        return entity;
    }

    private ProductVariantJpaEntity toVariantJpa(ProductVariant variant, ProductJpaEntity productEntity) {
        ProductVariantJpaEntity e = new ProductVariantJpaEntity();
        e.setId(variant.getId());
        e.setProduct(productEntity);
        e.setSku(variant.getSku());
        e.setColor(variant.getColor());
        e.setSize(variant.getSize());
        e.setPrice(variant.getPrice());
        e.setStockQty(variant.getStockQty());
        e.setAvailable(variant.isAvailable());
        return e;
    }

    // ──────────────────────────────────────────────
    // JPA → Domain
    // ──────────────────────────────────────────────

    public Product toDomain(ProductJpaEntity entity) {
        Product product = new Product(
                entity.getId(),
                entity.getName(),
                entity.getSlug(),
                entity.getCategoryId(),
                entity.getProductType(),
                entity.getDescription()
        );

        if (!entity.isActive()) product.deactivate();

        entity.getVariants().stream()
                .map(this::toVariantDomain)
                .forEach(product::addVariant);

        return product;
    }

    public ProductVariant toVariantDomain(ProductVariantJpaEntity e) {
        return new ProductVariant(
                e.getId(),
                e.getProduct().getId(),
                e.getSku(),
                e.getColor(),
                e.getSize(),
                e.getPrice(),
                e.getStockQty()
        );
    }
}
