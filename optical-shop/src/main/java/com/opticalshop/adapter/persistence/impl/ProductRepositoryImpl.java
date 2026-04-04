package com.opticalshop.adapter.persistence.impl;

import com.opticalshop.adapter.persistence.mapper.ProductMapper;
import com.opticalshop.adapter.persistence.repository.ProductJpaRepository;
import com.opticalshop.adapter.persistence.repository.ProductVariantJpaRepository;
import com.opticalshop.domain.model.product.Product;
import com.opticalshop.domain.model.product.ProductVariant;
import com.opticalshop.domain.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final ProductVariantJpaRepository variantJpaRepository;
    private final ProductMapper mapper;

    public ProductRepositoryImpl(ProductJpaRepository productJpaRepository,
                                  ProductVariantJpaRepository variantJpaRepository,
                                  ProductMapper mapper) {
        this.productJpaRepository = productJpaRepository;
        this.variantJpaRepository = variantJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Product save(Product product) {
        var entity = mapper.toJpa(product);
        var saved = productJpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return productJpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Product> findBySlug(String slug) {
        return productJpaRepository.findBySlug(slug).map(mapper::toDomain);
    }

    @Override
    public List<Product> findByCategoryId(int categoryId) {
        return productJpaRepository.findByCategoryId(categoryId).stream()
                .map(mapper::toDomain).toList();
    }

    @Override
    public List<Product> findActiveProducts() {
        return productJpaRepository.findByActiveTrue().stream()
                .map(mapper::toDomain).toList();
    }

    @Override
    public Optional<ProductVariant> findVariantById(UUID variantId) {
        return variantJpaRepository.findById(variantId).map(mapper::toVariantDomain);
    }

    @Override
    public ProductVariant saveVariant(ProductVariant variant) {
        // Tìm entity gốc, cập nhật stock, lưu lại
        var entity = variantJpaRepository.findById(variant.getId())
                .orElseThrow(() -> new RuntimeException("Variant not found: " + variant.getId()));
        entity.setStockQty(variant.getStockQty());
        entity.setAvailable(variant.isAvailable());
        var saved = variantJpaRepository.save(entity);
        return mapper.toVariantDomain(saved);
    }
}
