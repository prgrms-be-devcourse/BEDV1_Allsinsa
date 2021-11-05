package com.progm.allsinsa.cart.controller;

import com.progm.allsinsa.cart.dto.CartDto;
import com.progm.allsinsa.cart.service.CartService;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/carts")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // send cart products list by cartId
    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable("cartId") Long cartId)
        throws NotFoundException {
        CartDto dto = cartService.findCartById(cartId);
        return ResponseEntity.ok(dto);
    }
}
