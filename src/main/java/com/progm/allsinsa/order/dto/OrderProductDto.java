package com.progm.allsinsa.order.dto;

public record OrderProductDto(
    String productName,
    String productOption,
    String thumbnailImagePath,
    String orderStatus,
    int price,
    int quantity,
    long productId
) {
}
