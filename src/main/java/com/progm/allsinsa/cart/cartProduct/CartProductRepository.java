package com.progm.allsinsa.cart.cartProduct;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    List<CartProduct> findAllByCartId(Long cartId);
}
