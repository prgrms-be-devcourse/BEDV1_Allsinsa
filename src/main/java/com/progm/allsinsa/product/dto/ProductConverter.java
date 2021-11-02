package com.progm.allsinsa.product.dto;

import org.springframework.stereotype.Component;

import com.progm.allsinsa.product.domain.Product;

@Component
public class ProductConverter {
    public Product convertRequestToProduct(ProductRequestDto productRequestDto) {
        return new Product(productRequestDto.getName(), productRequestDto.getPrice(), productRequestDto.getCategory(),
                productRequestDto.getStatus(),
                productRequestDto.getProductDetailImgPath(), productRequestDto.getThumbnailImgPath(),
                productRequestDto.getSellerId());
    }

    public ProductDto convertToProductDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .category(product.getCategory())
                .status(product.getStatus())
                .productDetailImgPath(product.getProductDetailImgPath())
                .productDetailImgPath(product.getThumbnailImgPath())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .sellerId(product.getSellerId())
                .build();
    }
}
