package com.progm.allsinsa.order;

import com.progm.allsinsa.order.domain.Order;
import com.progm.allsinsa.order.dto.CreateOrderDto;
import com.progm.allsinsa.order.dto.CreateOrderProductDto;
import com.progm.allsinsa.order.dto.CreateOrderRequestDto;
import com.progm.allsinsa.order.dto.OrderDto;
import com.progm.allsinsa.order.repository.OrderRepository;
import com.progm.allsinsa.order.service.OrderService;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@EnableJpaAuditing
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Transactional
    @Test
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
                "https://www.google.com/url?sa=i&url=http%3A%2F%2Fwww.sportsseoul.com%2Fnews%2Fread%2F988300&psig=AOvVaw33h5sXl1K-OPcKgT7BmBGK&ust=1635391936561000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCPj-tfLT6fMCFQAAAAAdAAAAABAD",
                2L
        );
        CreateOrderProductDto createOrderProductDto2 = new CreateOrderProductDto(
                "신발",
                10000,
                1,
                "사이즈 : M",
                "https://www.google.com/url?sa=i&url=http%3A%2F%2Fwww.sportsseoul.com%2Fnews%2Fread%2F988300&psig=AOvVaw33h5sXl1K-OPcKgT7BmBGK&ust=1635391936561000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCPj-tfLT6fMCFQAAAAAdAAAAABAD",
                3L
        );
        CreateOrderRequestDto dto = new CreateOrderRequestDto(createOrderDto, List.of(createOrderProductDto1, createOrderProductDto2));

        String orderNumber = orderService.createOrder(dto);

        OrderDto orderDto = orderService.getOrder(orderNumber);
        System.out.println(orderDto);
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
                "https://www.google.com/url?sa=i&url=http%3A%2F%2Fwww.sportsseoul.com%2Fnews%2Fread%2F988300&psig=AOvVaw33h5sXl1K-OPcKgT7BmBGK&ust=1635391936561000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCPj-tfLT6fMCFQAAAAAdAAAAABAD",
                2L
        );
        CreateOrderProductDto createOrderProductDto2 = new CreateOrderProductDto(
                "신발",
                10000,
                1,
                "사이즈 : M",
                "https://www.google.com/url?sa=i&url=http%3A%2F%2Fwww.sportsseoul.com%2Fnews%2Fread%2F988300&psig=AOvVaw33h5sXl1K-OPcKgT7BmBGK&ust=1635391936561000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCPj-tfLT6fMCFQAAAAAdAAAAABAD",
                3L
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
                "https://www.google.com/url?sa=i&url=http%3A%2F%2Fwww.sportsseoul.com%2Fnews%2Fread%2F988300&psig=AOvVaw33h5sXl1K-OPcKgT7BmBGK&ust=1635391936561000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCPj-tfLT6fMCFQAAAAAdAAAAABAD",
                2L
        );
        CreateOrderProductDto createOrderProductDto4 = new CreateOrderProductDto(
                "신발",
                10000,
                1,
                "사이즈 : M",
                "https://www.google.com/url?sa=i&url=http%3A%2F%2Fwww.sportsseoul.com%2Fnews%2Fread%2F988300&psig=AOvVaw33h5sXl1K-OPcKgT7BmBGK&ust=1635391936561000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCPj-tfLT6fMCFQAAAAAdAAAAABAD",
                3L
        );
        CreateOrderRequestDto dto1 = new CreateOrderRequestDto(createOrderDto1, List.of(createOrderProductDto1, createOrderProductDto2));
        CreateOrderRequestDto dto2 = new CreateOrderRequestDto(createOrderDto2, List.of(createOrderProductDto3, createOrderProductDto4));

        String orderNumber1 = orderService.createOrder(dto1);
        String orderNumber2 = orderService.createOrder(dto2);

        Page<OrderDto> allOrder = orderService.getMemberOrder(PageRequest.of(0, 10, Sort.by("createdAt").descending()), 2L);
        System.out.println(allOrder.getContent());
    }
}
