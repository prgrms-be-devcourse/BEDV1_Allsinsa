package com.progm.allsinsa.product.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.progm.allsinsa.product.domain.ProductOption;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ProductOptionResponse {
    private Long id;
    private ProductDto productDto;
    private int stock;
    private String option1;
    private String option2;

    public static ProductOptionResponse from(ProductOption productOption) {
        return new ProductOptionResponse(
                productOption.getId(),
                ProductDto.from(productOption.getProduct()),
                productOption.getStock(),
                productOption.getOption1(),
                productOption.getOption2());
    }

    public static List<ProductOptionResponse> list(List<ProductOption> productOptions) {
        return productOptions.stream()
                .map(ProductOptionResponse::from)
                .collect(Collectors.toList());
    }
}
