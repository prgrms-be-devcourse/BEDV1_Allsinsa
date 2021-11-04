package com.progm.allsinsa.cart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progm.allsinsa.cart.dto.CartDto;
import com.progm.allsinsa.cart.service.CartService;
import javassist.NotFoundException;


@RestController
@RequestMapping("/api/v1/carts")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /*
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
    */
    // send cart products list by memberid
    @GetMapping
    public ResponseEntity<CartDto> getCart(@RequestBody CartDto cartDto)
            throws NotFoundException {
        CartDto dto = cartService.findCartById(cartDto.getId());
        return ResponseEntity.ok(dto);
    }
}
