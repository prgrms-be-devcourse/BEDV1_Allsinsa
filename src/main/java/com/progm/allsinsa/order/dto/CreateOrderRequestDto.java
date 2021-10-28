package com.progm.allsinsa.order.dto;

import java.util.List;

public record CreateOrderRequestDto(
    CreateOrderDto orderDto,
    List<CreateOrderProductDto> orderProductDtos
) {
}
