package com.progm.allsinsa.cart;

import static org.junit.jupiter.api.Assertions.*;

import com.progm.allsinsa.cart.cartProduct.CartProduct;
import com.progm.allsinsa.cart.cartProduct.CartProductDto;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import javassist.NotFoundException;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@DisplayName("CartServiceTest")
class CartServiceTest {

    @Autowired
    CartService cartService;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartConverter cartConverter;

    Logger log = LoggerFactory.getLogger(CartServiceTest.class);

    @Nested
    @DisplayName("장바구니 테스트")
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
    class CartProductTest {
        Long memberId = 200L;
        Long productOptionDto = 1L;
        Long cartProductId = 1L;

        private Long createCart(Long memberId) {
            Long cartId = cartService.createCart(memberId);
            log.info("create cart success - cartId : "+cartId.toString());
            return cartId;
        }

        @Test
        @DisplayName("장바구니에 제품 추가")
        @Order(1)
        void saveCartProduct() throws NotFoundException {
            CartProductDto cartProductDto = new CartProductDto(cartProductId, 0, productOptionDto);
            Long cartId = createCart(memberId);

            CartDto cartDto = cartService.findCartById(cartId);
            Cart cart = cartConverter.convertCart(cartDto);

            log.info("cart : "+cart.toString());

            Long resultCartProductId = cartService.saveCartProduct(cart.getId(), cartProductDto);

            log.info("cartProductId : "+resultCartProductId.toString()); // TODO : 2...?

            cartDto = cartService.findCartById(cartId);
            cart = cartConverter.convertCart(cartDto);

            cartDto = cartConverter.convertCartDto(cart);
            Optional<CartProductDto> resultCartProductDto = cartDto.getCartProductDtos()
                .stream().filter(t -> t.getId().equals(resultCartProductId))
                .findAny();

            //log.info("resultCartProductDto : "+resultCartProductDto.get().getId().toString());

            assertNotNull(resultCartProductDto);
            assertEquals(resultCartProductId, resultCartProductDto.get().getId());
        }
    }
}
