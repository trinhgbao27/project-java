package com.opticalshop.domain.repository;

import com.opticalshop.domain.model.product.Product;
import com.opticalshop.domain.model.product.ProductVariant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(UUID id);
    Optional<Product> findBySlug(String slug);
    List<Product> findByCategoryId(int categoryId);
    List<Product> findActiveProducts();
    Optional<ProductVariant> findVariantById(UUID variantId);
    ProductVariant saveVariant(ProductVariant variant);
}
