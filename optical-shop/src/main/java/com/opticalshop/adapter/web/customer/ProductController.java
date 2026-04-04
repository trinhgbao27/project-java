package com.opticalshop.adapter.web.customer;

import com.opticalshop.adapter.web.dto.response.ApiResponse;
import com.opticalshop.application.usecase.product.GetProductDetailQuery;
import com.opticalshop.application.usecase.product.GetProductDetailUseCase;
import com.opticalshop.application.usecase.product.ProductDetailResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final GetProductDetailUseCase getProductDetailUseCase;

    public ProductController(GetProductDetailUseCase getProductDetailUseCase) {
        this.getProductDetailUseCase = getProductDetailUseCase;
    }

    /**
     * GET /api/v1/products/{productId}
     * Lấy chi tiết sản phẩm theo ID.
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductDetailResult>> getProductDetail(
            @PathVariable UUID productId) {

        ProductDetailResult result = getProductDetailUseCase.execute(
                new GetProductDetailQuery(productId));

        return ResponseEntity.ok(ApiResponse.ok(result));
    }
}
