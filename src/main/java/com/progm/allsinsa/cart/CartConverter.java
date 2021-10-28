package com.progm.allsinsa.cart;

import com.progm.allsinsa.cart.cartProduct.CartProduct;
import com.progm.allsinsa.cart.cartProduct.CartProductDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CartConverter {

    public Cart convertCart(CartDto dto) {
        Cart cart = new Cart(
            dto.getId(),
            dto.getMemberDto()
        );
        this.convertCartProducts(dto).forEach(cart::addCartProduct);
        return cart;
    }

    public List<CartProduct> convertCartProducts(CartDto cartDto) {
        return cartDto.getCartProductDtos().stream()
            .map(cartProductDto -> {
                return new CartProduct(
                    cartProductDto.getId(),
                    cartProductDto.getCount(),
                    // TODO : ProductOption
                    cartProductDto.getProductOptionDto()
                    );
            })
            .collect(Collectors.toList());
    }

    public CartProduct convertCartProduct(Cart cart, CartProductDto cartProductDto) {
        CartProduct cartProduct = new CartProduct(
            cartProductDto.getId(),
            cartProductDto.getCount(),
            // TODO : ProductOption
            cartProductDto.getProductOptionDto()
        );
        cartProduct.setCart(cart);
        return cartProduct;
    }

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
