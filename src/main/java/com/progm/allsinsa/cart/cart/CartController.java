package com.progm.allsinsa.cart.cart;

import com.progm.allsinsa.cart.CartService;
import com.progm.allsinsa.cart.cartProduct.CartProductDto;
import java.util.List;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // create cart by member id
    @PostMapping("/{member_id}")
    public ResponseEntity<Long> createCart(@PathVariable("member_id") Long memberId) {
        Long cart = cartService.createCart(memberId);
        return ResponseEntity.ok(cart);
    }

    // delete cart by member
    @DeleteMapping("/{member_id}")
    public ResponseEntity<Long> deleteCart(@PathVariable("member_id") Long memberId)
        throws NotFoundException {
        cartService.deleteCart(memberId);
        return ResponseEntity.ok(memberId);
    }

    // send cart products list by memberid
    @GetMapping("/{member_id}")
    public ResponseEntity<CartDto> getCart(@PathVariable("member_id") Long memberId)
        throws NotFoundException {
        CartDto cartDto = cartService.findCartByMemberId(memberId);
        return ResponseEntity.ok(cartDto);
    }
}
