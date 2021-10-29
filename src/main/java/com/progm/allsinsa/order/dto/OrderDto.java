package com.progm.allsinsa.order.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
        String recipientName,
        String phoneNumber,
        String shippingAddress,
        String memo,
        String orderNumber,
        int totalAmount,
        int savedAmount,
        int paymentAmount,
        String orderStatus,
        LocalDateTime createdAt,
        List<OrderProductDto> orderProductDtos
) {
}
