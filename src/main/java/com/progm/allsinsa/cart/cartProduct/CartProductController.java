package com.progm.allsinsa.cart.cartProduct;

import com.progm.allsinsa.cart.CartService;
import java.util.List;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/cart-product")
public class CartProductController {
    private final CartService cartService;

    public CartProductController(CartService cartService) {
        this.cartService = cartService;
    }

    // save cart product
    @PostMapping
    public ResponseEntity<CartProductDto> saveCartProduct(@RequestBody CartProductDto cartProductDto)
        throws NotFoundException {
        CartProductDto dto = cartService.saveCartProduct(cartProductDto.getId(),
            cartProductDto);
        return ResponseEntity.ok(dto);
    }

    // delete cart product
    @DeleteMapping("/{cart_product_id}")
    public ResponseEntity<Long> deleteCartProduct(@PathVariable("cart_product_id") Long cartProductId)
        throws NotFoundException {
        cartService.deleteCartProduct(cartProductId);
        return ResponseEntity.ok(cartProductId);
    }

    // find one of cart product by cart product id
    @GetMapping("/{cart_product_id}")
    public ResponseEntity<CartProductDto> findCartProduct(@PathVariable("cart_product_id") Long cartProductId)
        throws NotFoundException {
        CartProductDto cartProductDto = cartService.findCartProductById(cartProductId);
        return ResponseEntity.ok(cartProductDto);
    }

    // fix cart product
    @PutMapping
    public ResponseEntity<CartProductDto> fixCartProduct(@RequestBody CartProductDto cartProductDto)
        throws NotFoundException {
        CartProductDto updatedCartProductDto = cartService.updateCartProduct(cartProductDto);
        return ResponseEntity.ok(updatedCartProductDto);
    }

}
