package com.progm.allsinsa.cart.cartProduct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CartProductDto {
    private Long id;
    private int count;

    // TODO : productOptionDto
    private Long productOptionDto;
}
