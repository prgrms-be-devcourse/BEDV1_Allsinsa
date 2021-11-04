package com.progm.allsinsa.product.dto;

import javax.validation.constraints.Positive;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ProductOptionStockDto {
    @Positive
    private int stock;

    public static ProductOptionStockDto from(int stock) {
        return new ProductOptionStockDto(stock);
    }
}
