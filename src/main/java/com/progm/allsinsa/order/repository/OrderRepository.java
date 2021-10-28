package com.progm.allsinsa.order.repository;

import com.progm.allsinsa.order.domain.Order;
import com.progm.allsinsa.order.domain.OrderProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderNumber(String orderNumber);
    Page<Order> findAllByMemberId(Pageable pageable, Long memberId);
}
