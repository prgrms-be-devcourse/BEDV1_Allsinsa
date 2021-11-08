package com.progm.allsinsa.order.dto;

public record CreateOrderDto(
        Long memberId,
        String recipientName,
        String phoneNumber,
        String shippingAddress,
        String memo,
        int totalAmount
) {
}
