package com.opticalshop.application.usecase.product;

import com.opticalshop.domain.exception.DomainException;
import com.opticalshop.domain.model.product.Product;
import com.opticalshop.domain.repository.ProductRepository;

import java.util.List;

public class GetProductDetailUseCase {

    private final ProductRepository productRepository;

    public GetProductDetailUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDetailResult execute(GetProductDetailQuery query) {
        Product product = productRepository.findById(query.productId())
                .orElseThrow(() -> new DomainException("Product not found: " + query.productId()));

        List<ProductDetailResult.VariantResult> variants = product.getVariants().stream()
                .map(v -> new ProductDetailResult.VariantResult(
                        v.getId(), v.getSku(), v.getColor(), v.getSize(),
                        v.getPrice(), v.getStockQty(), v.isAvailable()
                ))
                .toList();

        return new ProductDetailResult(
                product.getId(),
                product.getName(),
                product.getSlug(),
                product.getProductType(),
                product.getDescription(),
                variants
        );
    }
}
