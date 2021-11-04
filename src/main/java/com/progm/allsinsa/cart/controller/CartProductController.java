package com.progm.allsinsa.cart.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progm.allsinsa.cart.dto.CartProductDto;
import com.progm.allsinsa.cart.service.CartProductService;
import javassist.NotFoundException;

@RestController
@RequestMapping("/api/v1/cart-products")
public class CartProductController {
    private final CartProductService cartProductService;

    public CartProductController(CartProductService cartProductService) {
        this.cartProductService = cartProductService;
    }

    // save cart product
    @PostMapping
    public ResponseEntity<CartProductDto> saveCartProduct(@RequestBody CartProductDto cartProductDto)
            throws NotFoundException {
        CartProductDto dto = cartProductService.saveCartProduct(cartProductDto.getId(),
                cartProductDto);
        return ResponseEntity.created(
                URI.create("/api/v1/cart-product/" + dto.getId() + "/count/" + dto.getCount())
        ).body(dto);
    }

    // delete cart product
    @DeleteMapping("/{cart_product_id}")
    public ResponseEntity<Void> deleteCartProduct(@PathVariable("cart_product_id") Long cartProductId) {
        cartProductService.deleteCartProduct(cartProductId);
        return ResponseEntity.noContent().build();
    }

    // find one of cart product by cart product id
    @GetMapping("/{cart_product_id}")
    public ResponseEntity<CartProductDto> findCartProduct(@PathVariable("cart_product_id") Long cartProductId)
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
