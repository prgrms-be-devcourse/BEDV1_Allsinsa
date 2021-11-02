package com.progm.allsinsa.cart.dto;

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
