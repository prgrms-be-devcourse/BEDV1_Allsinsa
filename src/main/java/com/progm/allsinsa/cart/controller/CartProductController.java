package com.progm.allsinsa.cart.controller;

import com.progm.allsinsa.cart.dto.CartDto;
import com.progm.allsinsa.cart.dto.CartProductDto;
import com.progm.allsinsa.cart.service.CartProductService;
import java.net.URI;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cart-products")
public class CartProductController {
    private final CartProductService cartProductService;

    public CartProductController(CartProductService cartProductService) {
        this.cartProductService = cartProductService;
    }

    // save cart product
    @PostMapping
    public ResponseEntity<CartProductDto> saveCartProduct(@RequestBody CartDto cartDto)
        throws NotFoundException {
        CartProductDto dto = cartProductService.saveCartProduct(cartDto.getId(),
            cartDto.getCartProductDtos().get(0));
        return ResponseEntity.created(
            URI.create("/api/v1/cart-products/" + dto.getId() + "/count/" + dto.getCount())
        ).body(dto);
    }

    // delete cart product
    @DeleteMapping("/{cartProductId}")
    public ResponseEntity<Void> deleteCartProduct(@PathVariable("cartProductId") Long cartProductId) {
        cartProductService.deleteCartProduct(cartProductId);
        return ResponseEntity.noContent().build();
    }

    // find one of cart product by cartProductId
    @GetMapping("/{cartProductId}")
    public ResponseEntity<CartProductDto> findCartProduct(@PathVariable("cartProductId") Long cartProductId)
        throws NotFoundException {
        CartProductDto cartProductDto = cartProductService.findCartProductById(cartProductId);
        return ResponseEntity.ok(cartProductDto);
    }

    // fix cart product
    @PutMapping
    public ResponseEntity<CartProductDto> fixCartProduct(@RequestBody CartProductDto cartProductDto)
        throws NotFoundException {
        CartProductDto updatedCartProductDto = cartProductService.updateCartProduct(cartProductDto);
        return ResponseEntity.ok(updatedCartProductDto);
    }

}
