package com.progm.allsinsa.order.controller;

import com.progm.allsinsa.order.dto.CreateOrderRequestDto;
import com.progm.allsinsa.order.dto.OrderDto;
import com.progm.allsinsa.order.service.OrderService;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> notFoundHandler(NotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody CreateOrderRequestDto createOrderRequestDto) {
        String orderNumber = orderService.createOrder(createOrderRequestDto);
        return ResponseEntity.ok(orderNumber);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Page<OrderDto>> getMemberOrder(@PathVariable("id") Long memberId, Pageable pageable) {
        Page<OrderDto> memberOrderDto = orderService.getMemberOrder(pageable, memberId);
        return ResponseEntity.ok(memberOrderDto);
    }

    @GetMapping
    public ResponseEntity<Page<OrderDto>> getAllOrder(Pageable pageable) {
        Page<OrderDto> allOrder = orderService.getAllOrder(pageable);
        return ResponseEntity.ok(allOrder);
    }

    @GetMapping("/{number}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable("number") String orderNumber) throws NotFoundException {
        OrderDto orderDto = orderService.getOrder(orderNumber);
        return ResponseEntity.ok(orderDto);
    }

}
