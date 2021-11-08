package com.progm.allsinsa.cart.dto;

import com.progm.allsinsa.product.dto.ProductOptionResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CartProductDto {
    private Long id;
    private int count;

    private ProductOptionResponse productOptionDto;
}
