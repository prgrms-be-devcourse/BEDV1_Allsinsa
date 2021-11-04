package com.progm.allsinsa.cart.dto;

import com.progm.allsinsa.member.dto.MemberDto;
import java.util.List;
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
