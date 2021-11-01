package com.progm.allsinsa.order;

import com.progm.allsinsa.order.domain.Order;
import com.progm.allsinsa.order.dto.*;
import com.progm.allsinsa.order.repository.OrderRepository;
import com.progm.allsinsa.order.service.OrderService;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@EnableJpaAuditing
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderConverter orderConverter;

    private OrderDto orderDto;
    private String orderNumber;

    @Transactional
    @BeforeEach
    public void createOrderTest() throws NotFoundException {
        CreateOrderDto createOrderDto = new CreateOrderDto(
                1L,
                "김현준",
                "01026846867",
                "경기도 수원시 영통구 영통동",
                "부재시 경비실에 맡겨주세요",
                16000);
        CreateOrderProductDto createOrderProductDto1 = new CreateOrderProductDto(
                "바지",
                3000,
                2,
                "사이즈 : S",
                1L,
                "https://www.google.com/url?sa=i&url=http%3A%2F%2Fwww.sportsseoul.com%2Fnews%2Fread%2F988300&psig=AOvVaw33h5sXl1K-OPcKgT7BmBGK&ust=1635391936561000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCPj-tfLT6fMCFQAAAAAdAAAAABAD",
                1L
        );
        CreateOrderProductDto createOrderProductDto2 = new CreateOrderProductDto(
                "신발",
                10000,
                1,
                "사이즈 : M",
                3L,
                "https://www.google.com/url?sa=i&url=http%3A%2F%2Fwww.sportsseoul.com%2Fnews%2Fread%2F988300&psig=AOvVaw33h5sXl1K-OPcKgT7BmBGK&ust=1635391936561000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCPj-tfLT6fMCFQAAAAAdAAAAABAD",
                2L
        );

        CreateOrderRequestDto dto = new CreateOrderRequestDto(createOrderDto, List.of(createOrderProductDto1, createOrderProductDto2));

        orderNumber = orderService.createOrder(dto);
        orderDto = orderService.getOrder(orderNumber);

        Order retrievedOrder = orderRepository.findByOrderNumber(orderNumber).get();

        assertThat(orderDto, samePropertyValuesAs(orderConverter.convertOrderDto(retrievedOrder)));
    }

    @Test
    @Transactional
    public void getAllOrderTest() {
        CreateOrderDto createOrderDto1 = new CreateOrderDto(
                1L,
                "김현준1",
                "01026846867",
                "경기도 수원시 영통구 영통동",
                "부재시 경비실에 맡겨주세요",
                16000);
        CreateOrderProductDto createOrderProductDto1 = new CreateOrderProductDto(
                "바지",
                3000,
                2,
                "사이즈 : S",
                1L,
                "https://www.google.com/url?sa=i&url=http%3A%2F%2Fwww.sportsseoul.com%2Fnews%2Fread%2F988300&psig=AOvVaw33h5sXl1K-OPcKgT7BmBGK&ust=1635391936561000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCPj-tfLT6fMCFQAAAAAdAAAAABAD",
                1L
        );
        CreateOrderProductDto createOrderProductDto2 = new CreateOrderProductDto(
                "신발",
                10000,
                1,
                "사이즈 : M",
                5L,
                "https://www.google.com/url?sa=i&url=http%3A%2F%2Fwww.sportsseoul.com%2Fnews%2Fread%2F988300&psig=AOvVaw33h5sXl1K-OPcKgT7BmBGK&ust=1635391936561000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCPj-tfLT6fMCFQAAAAAdAAAAABAD",
                2L
        );
        CreateOrderDto createOrderDto2 = new CreateOrderDto(
                2L,
                "김현준2",
                "01026846867",
                "경기도 수원시 영통구 영통동",
                "부재시 경비실에 맡겨주세요",
                16000);

        CreateOrderProductDto createOrderProductDto3 = new CreateOrderProductDto(
                "바지",
                3000,
                2,
                "사이즈 : S",
                1L,
                "https://www.google.com/url?sa=i&url=http%3A%2F%2Fwww.sportsseoul.com%2Fnews%2Fread%2F988300&psig=AOvVaw33h5sXl1K-OPcKgT7BmBGK&ust=1635391936561000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCPj-tfLT6fMCFQAAAAAdAAAAABAD",
                1L
        );
        CreateOrderProductDto createOrderProductDto4 = new CreateOrderProductDto(
                "신발",
                10000,
                1,
                "사이즈 : M",
                3L,
                "https://www.google.com/url?sa=i&url=http%3A%2F%2Fwww.sportsseoul.com%2Fnews%2Fread%2F988300&psig=AOvVaw33h5sXl1K-OPcKgT7BmBGK&ust=1635391936561000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCPj-tfLT6fMCFQAAAAAdAAAAABAD",
                2L
        );
        CreateOrderRequestDto dto1 = new CreateOrderRequestDto(createOrderDto1, List.of(createOrderProductDto1, createOrderProductDto2));
        CreateOrderRequestDto dto2 = new CreateOrderRequestDto(createOrderDto2, List.of(createOrderProductDto3, createOrderProductDto4));

        String orderNumber1 = orderService.createOrder(dto1);
        String orderNumber2 = orderService.createOrder(dto2);

        Page<OrderDto> allOrder = orderService.getAllOrder(PageRequest.of(0, 10, Sort.by("createdAt").descending()));
        assertThat(allOrder.getContent().size(), is(3));
    }

    @Test
    @Transactional
    public void getMemberOrderTest() {
        CreateOrderDto createOrderDto1 = new CreateOrderDto(
                1L,
                "김현준1",
                "01026846867",
                "경기도 수원시 영통구 영통동",
                "부재시 경비실에 맡겨주세요",
                16000);
        CreateOrderProductDto createOrderProductDto1 = new CreateOrderProductDto(
                "바지",
                3000,
                2,
                "사이즈 : S",
                1L,
                "https://www.google.com/url?sa=i&url=http%3A%2F%2Fwww.sportsseoul.com%2Fnews%2Fread%2F988300&psig=AOvVaw33h5sXl1K-OPcKgT7BmBGK&ust=1635391936561000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCPj-tfLT6fMCFQAAAAAdAAAAABAD",
                2L
        );
        CreateOrderProductDto createOrderProductDto2 = new CreateOrderProductDto(
                "신발",
                10000,
                1,
                "사이즈 : M",
                5L,
                "https://www.google.com/url?sa=i&url=http%3A%2F%2Fwww.sportsseoul.com%2Fnews%2Fread%2F988300&psig=AOvVaw33h5sXl1K-OPcKgT7BmBGK&ust=1635391936561000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCPj-tfLT6fMCFQAAAAAdAAAAABAD",
                2L
        );
        CreateOrderDto createOrderDto2 = new CreateOrderDto(
                2L,
                "김현준2",
                "01026846867",
                "경기도 수원시 영통구 영통동",
                "부재시 경비실에 맡겨주세요",
                16000);

        CreateOrderProductDto createOrderProductDto3 = new CreateOrderProductDto(
                "바지",
                3000,
                2,
                "사이즈 : S",
                1L,
                "https://www.google.com/url?sa=i&url=http%3A%2F%2Fwww.sportsseoul.com%2Fnews%2Fread%2F988300&psig=AOvVaw33h5sXl1K-OPcKgT7BmBGK&ust=1635391936561000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCPj-tfLT6fMCFQAAAAAdAAAAABAD",
                1L
        );
        CreateOrderProductDto createOrderProductDto4 = new CreateOrderProductDto(
                "신발",
                10000,
                1,
                "사이즈 : M",
                2L,
                "https://www.google.com/url?sa=i&url=http%3A%2F%2Fwww.sportsseoul.com%2Fnews%2Fread%2F988300&psig=AOvVaw33h5sXl1K-OPcKgT7BmBGK&ust=1635391936561000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCPj-tfLT6fMCFQAAAAAdAAAAABAD",
                2L
        );
        CreateOrderRequestDto dto1 = new CreateOrderRequestDto(createOrderDto1, List.of(createOrderProductDto1, createOrderProductDto2));
        CreateOrderRequestDto dto2 = new CreateOrderRequestDto(createOrderDto2, List.of(createOrderProductDto3, createOrderProductDto4));

        String orderNumber1 = orderService.createOrder(dto1);
        String orderNumber2 = orderService.createOrder(dto2);

        Page<OrderDto> memberOrder1 = orderService.getMemberOrder(PageRequest.of(0, 10, Sort.by("createdAt").descending()), 1L);
        Page<OrderDto> memberOrder2 = orderService.getMemberOrder(PageRequest.of(0, 10, Sort.by("createdAt").descending()), 2L);
        assertThat(memberOrder1.getContent().size(), is(2));
        assertThat(memberOrder2.getContent().size(), is(1));
    }
}
