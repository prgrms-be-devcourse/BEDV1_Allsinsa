package com.progm.allsinsa.cart.cart;

import com.progm.allsinsa.cart.cartProduct.CartProductDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CartDto {
    private Long id;

    // TODO : memberDto
    private Long memberDto;
    private List<CartProductDto> cartProductDtos;
}
