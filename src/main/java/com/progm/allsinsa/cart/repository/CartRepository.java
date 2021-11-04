package com.progm.allsinsa.cart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.progm.allsinsa.cart.domain.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findCartByMemberId(Long memberId);
}
