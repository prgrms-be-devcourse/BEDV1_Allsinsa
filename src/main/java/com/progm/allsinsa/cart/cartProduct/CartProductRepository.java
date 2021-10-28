package com.progm.allsinsa.cart.cartProduct;

import com.progm.allsinsa.cart.Cart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
}
