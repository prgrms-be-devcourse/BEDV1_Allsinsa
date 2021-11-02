package com.progm.allsinsa.order;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.Transactional;

import com.progm.allsinsa.order.domain.Order;
import com.progm.allsinsa.order.domain.OrderProduct;
import com.progm.allsinsa.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableJpaAuditing
@SpringBootTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository repository;

    private Order order;
    private String orderNumber;
    private Long memberId;
    private List<Order> orderList;

    @BeforeEach
    void setup() {
        memberId = 1L;
        orderList = new ArrayList<>();

        OrderProduct orderProduct1 = new OrderProduct("바지",
                3000,
                2,
                "사이즈 : S",
                "https://www.google.com/url?sa=i&url=http%3A%2F%2Fwww.sportsseoul.com%2Fnews%2Fread%2F988300&psig=AOvVaw33h5sXl1K-OPcKgT7BmBGK&ust=1635391936561000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCPj-tfLT6fMCFQAAAAAdAAAAABAD",
                2L, 2L);

        OrderProduct orderProduct2 = new OrderProduct("신발",
                10000,
                1,
                "사이즈 : M",
                "https://www.google.com/url?sa=i&url=http%3A%2F%2Fwww.sportsseoul.com%2Fnews%2Fread%2F988300&psig=AOvVaw33h5sXl1K-OPcKgT7BmBGK&ust=1635391936561000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCPj-tfLT6fMCFQAAAAAdAAAAABAD",
                3L, 5L);

        order = new Order(memberId,
                "김현준",
                "01026846867",
                "경기도 수원시 영통구 영통동",
                "부재시 경비실에 맡겨주세요",
                16000);
        order.addOrderProduct(orderProduct1);
        order.addOrderProduct(orderProduct2);

        repository.save(order);
        orderNumber = order.getOrderNumber();
        orderList.add(order);
    }

    @Test
    @DisplayName("주문번호로 조회")
    @Transactional
    void findByOrderNumberTest() {
        Optional<Order> byOrderNumber = repository.findByOrderNumber(orderNumber);
        assertThat(byOrderNumber.isEmpty(), is(false));

        Order retrievedOrder = byOrderNumber.get();
        assertThat(retrievedOrder, samePropertyValuesAs(order));
    }

    @Test
    @DisplayName("멤버별 모든 주문 조회")
    @Transactional
    void findAllByMemberIdTest() {
        Page<Order> allByMemberId = repository.findAllByMemberId(PageRequest.of(0, 4), memberId);
        System.out.println(orderList);
        List<Order> content = allByMemberId.getContent();

        assertThat(orderList.size(), is(content.size()));
        assertThat(orderList.containsAll(content), is(true));
    }
}
