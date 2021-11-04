package com.progm.allsinsa.cart.dto;

import com.progm.allsinsa.product.dto.ProductOptionResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CartProductDto {
    private Long id;
    private int count;

    private ProductOptionResponse productOptionDto;
}
