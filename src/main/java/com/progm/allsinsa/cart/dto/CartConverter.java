package com.progm.allsinsa.cart.dto;

import com.progm.allsinsa.cart.domain.Cart;
import com.progm.allsinsa.cart.domain.CartProduct;
import com.progm.allsinsa.cart.dto.CartDto;
import com.progm.allsinsa.cart.dto.CartProductDto;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

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
