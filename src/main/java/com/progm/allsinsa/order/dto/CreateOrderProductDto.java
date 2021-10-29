package com.progm.allsinsa.order.dto;

public record CreateOrderProductDto(
        String productName,
        int price,
        int quantity,
        String productOption,
        long productOptionId,
        String thumbnailImagePath,
        Long productId
) {
}
