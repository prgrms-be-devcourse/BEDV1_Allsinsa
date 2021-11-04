package com.progm.allsinsa.cart.repository;

import com.progm.allsinsa.cart.domain.CartProduct;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    List<CartProduct> findAllByCartId(Long cartId);
}
