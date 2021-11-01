package com.progm.allsinsa.order.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.progm.allsinsa.order.domain.Order;
import com.progm.allsinsa.order.domain.OrderProduct;

@Component
public class OrderConverter {

    public Order convertOrder(CreateOrderDto createOrderDto) {
        return new Order(createOrderDto.memberId(),
                createOrderDto.recipientName(),
                createOrderDto.phoneNumber(),
                createOrderDto.shippingAddress(),
                createOrderDto.memo(),
                createOrderDto.totalAmount());
    }

    public OrderDto convertOrderDto(Order order) {
        List<OrderProductDto> orderProductDtos = order.getOrderProducts().stream()
                .map(this::convertOrderProductDto)
                .collect(Collectors.toList());
        return new OrderDto(order.getRecipientName(),
                order.getOrderNumber(),
                order.getPhoneNumber(),
                order.getShippingAddress(),
                order.getMemo(),
                order.getTotalAmount(),
                order.getSavedAmount(),
                order.getPaymentAmount(),
                order.getOrderStatus().toString(),
                order.getCreatedAt(),
                orderProductDtos
        );
    }

    public OrderProduct convertOrderProduct(CreateOrderProductDto createOrderProductDto) {
        return new OrderProduct(createOrderProductDto.productName(),
                createOrderProductDto.price(),
                createOrderProductDto.quantity(),
                createOrderProductDto.productOption(),
                createOrderProductDto.thumbnailImagePath(),
                createOrderProductDto.productId(),
                createOrderProductDto.productOptionId());
    }

    public OrderProductDto convertOrderProductDto(OrderProduct orderProduct) {
        return new OrderProductDto(orderProduct.getProductName(),
                orderProduct.getProductOption(),
                orderProduct.getThumbnailImagePath(),
                orderProduct.getOrderStatus().toString(),
                orderProduct.getPrice(),
                orderProduct.getPrice(),
                orderProduct.getProductId());
    }

}
