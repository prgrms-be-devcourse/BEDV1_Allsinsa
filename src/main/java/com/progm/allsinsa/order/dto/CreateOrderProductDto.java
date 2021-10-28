package com.progm.allsinsa.order.dto;

public record CreateOrderProductDto(
        String productName,
        int price,
        int quantity,
        String productOption,
        String thumbnailImagePath,
        Long productId
) {
}
