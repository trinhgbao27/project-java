package com.opticalshop.adapter.web.manager;

import com.opticalshop.adapter.web.dto.response.ApiResponse;
import com.opticalshop.application.usecase.product.GetProductDetailQuery;
import com.opticalshop.application.usecase.product.GetProductDetailUseCase;
import com.opticalshop.application.usecase.product.ProductDetailResult;
import com.opticalshop.domain.model.product.Product;
import com.opticalshop.domain.model.product.ProductType;
import com.opticalshop.domain.repository.ProductRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/manager/products")
public class ProductManagementController {

    private final ProductRepository productRepository;
    private final GetProductDetailUseCase getProductDetailUseCase;

    public ProductManagementController(ProductRepository productRepository,
                                        GetProductDetailUseCase getProductDetailUseCase) {
        this.productRepository = productRepository;
        this.getProductDetailUseCase = getProductDetailUseCase;
    }

    /**
     * POST /api/v1/manager/products
     * Tạo sản phẩm mới.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ProductDetailResult>> createProduct(
            @Valid @RequestBody CreateProductRequest request) {

        Product product = Product.create(
                request.name(),
                request.slug(),
                request.categoryId(),
                request.productType(),
                request.description()
        );

        productRepository.save(product);

        ProductDetailResult result = getProductDetailUseCase.execute(
                new GetProductDetailQuery(product.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(result));
    }

    /**
     * DELETE /api/v1/manager/products/{productId}
     * Deactivate sản phẩm (soft delete).
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<Void>> deactivateProduct(@PathVariable UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new com.opticalshop.domain.exception.DomainException(
                        "Product not found: " + productId));

        product.deactivate();
        productRepository.save(product);

        return ResponseEntity.ok(ApiResponse.ok(null, "Product deactivated"));
    }

    // ── Inner record dùng làm request body ──
    public record CreateProductRequest(
            @NotBlank String name,
            @NotBlank String slug,
            int categoryId,
            @NotNull ProductType productType,
            String description
    ) {}
}
