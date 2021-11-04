package com.progm.allsinsa.cart.dto;

import java.util.List;

import com.progm.allsinsa.member.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CartDto {
    private Long id;

    private MemberDto memberDto;
    private List<CartProductDto> cartProductDtos;
}
