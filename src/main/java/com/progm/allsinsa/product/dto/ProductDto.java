package com.progm.allsinsa.product.dto;

import com.progm.allsinsa.product.domain.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;

    public static ProductDto from(Product product) {
        return new ProductDto(product.getId(), product.getName());
    }
}
