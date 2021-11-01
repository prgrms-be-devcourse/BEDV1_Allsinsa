package com.progm.allsinsa.product.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progm.allsinsa.product.dto.ProductOptionNameRequest;
import com.progm.allsinsa.product.dto.ProductOptionRequest;
import com.progm.allsinsa.product.dto.ProductOptionResponse;
import com.progm.allsinsa.product.dto.ProductOptionStockRequest;
import com.progm.allsinsa.product.service.ProductOptionService;

@RequestMapping("/api/v1/products/{productId}/productOptions")
@RestController
public class ProductOptionController {

    private final ProductOptionService productOptionService;

    public ProductOptionController(ProductOptionService productOptionService) {
        this.productOptionService = productOptionService;
    }

    @PostMapping
    public ResponseEntity<ProductOptionResponse> createProductOption(@PathVariable Long productId,
            @RequestBody @Valid ProductOptionRequest productOptionRequest) {
        ProductOptionResponse response = productOptionService.create(productId, productOptionRequest);

        return ResponseEntity.created(
                        URI.create("/api/v1/products/" + productId + "/productOptions/" + response.getId()))
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductOptionResponse>> findAllByProduct(@PathVariable Long productId) {
        List<ProductOptionResponse> responses = productOptionService.findAllByProduct(productId);
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{productOptionId}/optionName")
    public ResponseEntity<ProductOptionResponse> updateOptionName(
            @PathVariable Long productId,
            @PathVariable Long productOptionId,
            @RequestBody @Valid ProductOptionNameRequest optionNameRequest) {

        ProductOptionResponse response = productOptionService.updateOptionName(productOptionId, optionNameRequest);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{productOptionId}/stock")
    public ResponseEntity<Integer> addStock(
            @PathVariable Long productOptionId,
            @RequestBody @Valid ProductOptionStockRequest request) {
        int stock = productOptionService.addStock(productOptionId, request.getAdditionalStock());
        return ResponseEntity.ok(stock);
    }

    @DeleteMapping("/{productOptionId}")
    public ResponseEntity<Void> deleteProductOption(@PathVariable Long productId, @PathVariable Long productOptionId) {
        productOptionService.delete(productOptionId);
        return ResponseEntity.noContent().build();
    }
}
