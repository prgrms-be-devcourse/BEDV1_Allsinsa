package com.progm.allsinsa.cart.dto;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.progm.allsinsa.cart.domain.Cart;
import com.progm.allsinsa.cart.domain.CartProduct;
import com.progm.allsinsa.member.dto.MemberConverter;
import com.progm.allsinsa.product.service.ProductOptionService;
import javassist.NotFoundException;

@Component
public class CartConverter {
    private final MemberConverter memberConverter;
    private final ProductOptionService productOptionService;

    public CartConverter(MemberConverter memberConverter,
        ProductOptionService productOptionService) {
        this.memberConverter = memberConverter;
        this.productOptionService = productOptionService;
    }

    public CartDto convertCartDto(Cart cart) {
        return CartDto.builder()
            .id(cart.getId())
            .memberDto(memberConverter.convertMemberDto(cart.getMember()))
            .cartProductDtos(cart.getCartProducts().stream()
                .map(host -> {
                    try {
                        return this.convertCartProductDto(host);
                    } catch (NotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList())
            )
            .build();
    }

    public CartProductDto convertCartProductDto(CartProduct cartProduct) throws NotFoundException {
        return CartProductDto.builder()
            .id(cartProduct.getId())
            .count(cartProduct.getCount())
            .productOptionDto(productOptionService.findById(cartProduct.getProductOption().getId()))
            .build();
    }

}
