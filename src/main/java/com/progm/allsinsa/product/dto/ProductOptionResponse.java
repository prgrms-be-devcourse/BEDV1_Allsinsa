package com.progm.allsinsa.product.dto;

import com.progm.allsinsa.product.domain.Product;
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
    private Product product;
    private int stock;
    private String option1;
    private String option2;

    public static ProductOptionResponse from(ProductOption productOption) {
        return new ProductOptionResponse(
                productOption.getId(),
                productOption.getProduct(),
                productOption.getStock(),
                productOption.getOption1(),
                productOption.getOption2());
    }
}
