package com.progm.allsinsa.cart;

import com.progm.allsinsa.cart.cart.Cart;
import com.progm.allsinsa.cart.cart.CartDto;
import com.progm.allsinsa.cart.cartProduct.CartProduct;
import com.progm.allsinsa.cart.cartProduct.CartProductDto;
import java.util.List;
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
