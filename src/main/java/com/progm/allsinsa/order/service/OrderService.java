package com.progm.allsinsa.order.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.progm.allsinsa.order.domain.Order;
import com.progm.allsinsa.order.dto.CreateOrderRequestDto;
import com.progm.allsinsa.order.dto.OrderConverter;
import com.progm.allsinsa.order.dto.OrderDto;
import com.progm.allsinsa.order.repository.OrderRepository;
import javassist.NotFoundException;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;

    public OrderService(OrderRepository orderRepository, OrderConverter orderConverter) {
        this.orderRepository = orderRepository;
        this.orderConverter = orderConverter;
    }

    @Transactional
    public String createOrder(CreateOrderRequestDto createOrderRequestDto) {
        Order order = orderConverter.convertOrder(createOrderRequestDto.orderDto());
        createOrderRequestDto.orderProductDtos().stream()
                .map(orderConverter::convertOrderProduct)
                .forEach(order::addOrderProduct);
        orderRepository.save(order);
        return order.getOrderNumber();
    }

    @Transactional(readOnly = true)
    public OrderDto getOrder(String orderNumber) throws NotFoundException {
        return orderConverter.convertOrderDto(orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new NotFoundException(orderNumber + "해당 번호의 주문 정보가 없습니다.")));
    }

    @Transactional(readOnly = true)
    public Page<OrderDto> getMemberOrder(Pageable pageable, Long memberId) {
        return orderRepository.findAllByMemberId(pageable, memberId)
                .map(orderConverter::convertOrderDto);
    }

    @Transactional(readOnly = true)
    public Page<OrderDto> getAllOrder(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(orderConverter::convertOrderDto);
    }
}
