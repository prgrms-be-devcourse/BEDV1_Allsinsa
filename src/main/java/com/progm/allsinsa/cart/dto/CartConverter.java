package com.progm.allsinsa.cart.dto;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.progm.allsinsa.cart.domain.Cart;
import com.progm.allsinsa.cart.domain.CartProduct;

@Component
public class CartConverter {

    public CartDto convertCartDto(Cart cart) {
        return CartDto.builder()
                .id(cart.getId())
                .memberDto(cart.getMember())
                .cartProductDtos(cart.getCartProducts().stream()
                        .map(this::convertCartProductDto)
                        .collect(Collectors.toList())
                )
                .build();
    }

    public CartProductDto convertCartProductDto(CartProduct cartProduct) {
        return CartProductDto.builder()
                .id(cartProduct.getId())
                .count(cartProduct.getCount())
                // TODO : ProductOption converter
                .productOptionDto(cartProduct.getProductOption())
                .build();
    }

}
