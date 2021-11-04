package com.progm.allsinsa.cart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.progm.allsinsa.cart.domain.CartProduct;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    List<CartProduct> findAllByCartId(Long cartId);
}
