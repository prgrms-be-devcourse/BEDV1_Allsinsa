package com.progm.allsinsa.order;

import com.progm.allsinsa.order.domain.Order;
import com.progm.allsinsa.order.domain.OrderProduct;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Slf4j
@SpringBootTest
public class OrderTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    public void 오더생성_테스트() {
        // given
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        OrderProduct orderProduct = new OrderProduct("바지",
                3000,
                2,
                "사이즈 : S",
                "https://www.google.com/url?sa=i&url=http%3A%2F%2Fwww.sportsseoul.com%2Fnews%2Fread%2F988300&psig=AOvVaw33h5sXl1K-OPcKgT7BmBGK&ust=1635391936561000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCPj-tfLT6fMCFQAAAAAdAAAAABAD",
                2L);
        Order order = new Order(1L,
                "김현준",
                "01026846867",
                "경기도 수원시 영통구 영통동",
                "부재시 경비실에 맡겨주세요",
                6000);
        order.addOrderProduct(orderProduct);

        // when
        em.persist(order);
        tx.commit();

        // then
        Order retrievedOrder = em.find(Order.class, 1L);
        assertThat(order, samePropertyValuesAs(retrievedOrder));
    }
}
