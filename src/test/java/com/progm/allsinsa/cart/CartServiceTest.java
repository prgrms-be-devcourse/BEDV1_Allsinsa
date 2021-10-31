package com.progm.allsinsa.cart;

import static org.junit.jupiter.api.Assertions.*;

import com.progm.allsinsa.cart.cart.Cart;
import com.progm.allsinsa.cart.cart.CartDto;
import com.progm.allsinsa.cart.cart.CartRepository;
import com.progm.allsinsa.cart.cart.CartService;
import com.progm.allsinsa.cart.cartProduct.CartProductDto;
import com.progm.allsinsa.cart.cartProduct.CartProductService;
import java.util.Optional;
import javassist.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

@DisplayName("CartServiceTest")
@SpringBootTest
@TestMethodOrder(value = OrderAnnotation.class)
class CartServiceTest {

    @Autowired
    CartService cartService;

    @Autowired
    CartProductService cartProductService;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartConverter cartConverter;

    Logger log = LoggerFactory.getLogger(CartServiceTest.class);

    @Test
    void wrongDeleteCart() {
        assertThrows(EmptyResultDataAccessException.class, ()->cartRepository.deleteById(150L));
    }

    @Nested
    @DisplayName("장바구니 테스트")
    @TestMethodOrder(OrderAnnotation.class)
    class CartTest {
        private final Long memberId = 100L;

        @Test
        @Order(1)
        @DisplayName("맴버 생성시 장바구니 생성을 위한 테스트")
        void createCart() {
            Long cartId = cartService.createCart(memberId);
            Optional<Cart> byId = cartRepository.findById(cartId);
            assertNotNull(byId);
            Cart cart = byId.get();
            assertEquals(cartId, cart.getId());
            assertEquals(memberId, cart.getMember());
            log.info("장바구니 생성 성공");
        }

        @Test
        @Order(2)
        @DisplayName("맴버 삭제시 장바구니 삭제를 위한 테스트")
        void deleteCart() {
            Cart cart = cartRepository.findCartByMember(memberId).get();
            assertDoesNotThrow(() -> cartService.deleteCart(cart.getId()));

            assertThrows(NotFoundException.class, () -> cartService.findCartByMemberId(memberId));
            log.info("장바구니 삭제 성공");
        }
    }

    @Nested
    @DisplayName("장바구니 제품 테스트")
    @TestMethodOrder(OrderAnnotation.class)
    class CartProductTest {
        Long memberId = 200L;
        Long productOptionDto = 1L;

        private Long createCart(Long memberId) {
            Long cartId = cartService.createCart(memberId);
            log.info("create cart success - cartId : "+cartId.toString());
            return cartId;
        }

        @Test
        @Order(1)
        @DisplayName("장바구니에 제품 추가")
        void saveCartProduct() throws NotFoundException {
            CartProductDto cartProductDto = new CartProductDto(null, 0, productOptionDto);
            Long cartId = createCart(memberId);

            CartDto cartDto = cartService.findCartById(cartId);

            CartProductDto cPDto = cartProductService.saveCartProduct(cartDto.getId(),
                cartProductDto);

            Long resultCartProductId = cPDto.getId();

            log.info("cartProductId : "+resultCartProductId.toString()); // TODO : 2...?

            cartDto = cartService.findCartById(cartId);

            Optional<CartProductDto> resultCartProductDto = cartDto.getCartProductDtos()
                .stream().filter(t -> t.getId().equals(resultCartProductId))
                .findAny();

            assertNotNull(resultCartProductDto);
            assertEquals(resultCartProductId, resultCartProductDto.get().getId());
        }

        @Test
        @Order(2)
        @DisplayName("장바구니의 제품 수정")
        void updateCartProduct() throws NotFoundException {
            int fixedCount = 100;
            CartDto cartDto = cartService.findCartByMemberId(memberId);
            CartProductDto cartProductDto = cartDto.getCartProductDtos().get(0);

            CartProductDto newCartProductDto = new CartProductDto(cartProductDto.getId(), fixedCount, cartProductDto.getProductOptionDto());

            CartProductDto resultCartProductDto = cartProductService.updateCartProduct(newCartProductDto);
            CartProductDto cartProductById = cartProductService.findCartProductById(
                resultCartProductDto.getId());

            assertNotNull(cartProductById);
            assertEquals(fixedCount, cartProductById.getCount());
        }

        @Test
        @Order(3)
        @DisplayName("장바구니의 제품 삭제")
        void deleteCartProduct() throws NotFoundException {
            CartDto cartDto = cartService.findCartByMemberId(memberId);
            CartProductDto cartProductDto = cartDto.getCartProductDtos().get(0);

            cartProductService.deleteCartProduct(cartProductDto.getId());

            assertThrows(NotFoundException.class, () -> cartProductService.findCartProductById(cartProductDto.getId()));
        }
    }
}
