package com.progm.allsinsa.cart;

import static org.junit.jupiter.api.Assertions.*;

import com.progm.allsinsa.cart.domain.Cart;
import com.progm.allsinsa.cart.dto.CartConverter;
import com.progm.allsinsa.cart.dto.CartDto;
import com.progm.allsinsa.cart.dto.CartProductDto;
import com.progm.allsinsa.cart.repository.CartRepository;
import com.progm.allsinsa.cart.service.CartProductService;
import com.progm.allsinsa.cart.service.CartService;
import com.progm.allsinsa.member.domain.Member;
import com.progm.allsinsa.member.dto.MemberConverter;
import com.progm.allsinsa.member.dto.MemberDto;
import com.progm.allsinsa.member.repository.MemberRepository;
import com.progm.allsinsa.member.service.MemberService;
import com.progm.allsinsa.product.dto.ProductOptionRequest;
import com.progm.allsinsa.product.dto.ProductOptionResponse;
import com.progm.allsinsa.product.dto.ProductRequestDto;
import com.progm.allsinsa.product.service.ProductOptionService;
import com.progm.allsinsa.product.service.ProductService;
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

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberConverter memberConverter;

    @Autowired
    MemberService memberService;

    @Autowired
    ProductService productService;

    @Autowired
    ProductOptionService productOptionService;

    Logger log = LoggerFactory.getLogger(CartServiceTest.class);

    @Test
    void wrongDeleteCart() {
        assertThrows(EmptyResultDataAccessException.class, ()->cartRepository.deleteById(150L));
    }

    @Nested
    @DisplayName("장바구니 테스트")
    @TestMethodOrder(OrderAnnotation.class)
    class CartTest {

        @Test
        @Order(1)
        @DisplayName("맴버 생성시 장바구니 생성을 위한 테스트")
        void createCart() throws NotFoundException {
            MemberDto createdMemberDto = MemberDto.builder()
                .name("A")
                .email("1234@abc.com")
                .password("123abcDEF")
                .build();
            MemberDto memberDto = memberService.createMember(createdMemberDto);
            CartDto cartDto = cartService.findCartByMemberId(memberDto.getId());
            Optional<Cart> byId = cartRepository.findById(cartDto.getId());
            assertNotNull(byId);
            Cart cart = byId.get();
            assertEquals(cartDto.getId(), cart.getId());
            assertEquals(memberDto.getId(), memberConverter.convertMemberDto(cart.getMember()).getId());
            log.info("장바구니 생성 성공");
        }

        @Test
        @Order(2)
        @DisplayName("맴버 삭제시 장바구니 삭제를 위한 테스트")
        void deleteCart() {
            Member member = memberRepository.findByName("A").get();
            Long memberId = member.getId();
            Cart cart = cartRepository.findCartByMemberId(member.getId()).get();
            assertDoesNotThrow(() -> cartService.deleteCart(cart.getId()));

            assertThrows(NotFoundException.class, () -> cartService.findCartByMemberId(memberId));
            log.info("장바구니 삭제 성공");
        }
    }

    @Nested
    @DisplayName("장바구니 제품 테스트")
    @TestMethodOrder(OrderAnnotation.class)
    class CartProductTest {

        private CartDto createCart() throws NotFoundException {
            MemberDto createdMemberDto = MemberDto.builder()
                .name("B")
                .email("2234@abc.com")
                .password("123abcDEF")
                .build();
            MemberDto memberDto = memberService.createMember(createdMemberDto);
            CartDto cartDto = cartService.findCartByMemberId(memberDto.getId());
            log.info("create cart success - cartId : "+cartDto.toString());
            return cartDto;
        }

        @Test
        @Order(1)
        @DisplayName("장바구니에 제품 추가")
        void saveCartProduct() throws NotFoundException {
            // create Member & Cart
            CartDto cartDto = createCart();
            Long cartId = cartDto.getId();

            // create product
            ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .name("productA")
                .build();
            long productId = productService.save(productRequestDto);

            // create product option
            ProductOptionRequest productOptionRequest = ProductOptionRequest.builder()
                .stock(20)
                .option1("L")
                .build();
            ProductOptionResponse productOptionResponse = productOptionService.create(productId,
                productOptionRequest);

            // save to cart
            CartProductDto draftDto = CartProductDto.builder()
                .count(2)
                .productOptionDto(productOptionResponse)
                .build();
            CartProductDto CartProductDto = cartProductService.saveCartProduct(cartId,
                draftDto);

            Long cartProductId = CartProductDto.getId();

            log.info("cartProductId : "+cartProductId.toString());

            cartDto = cartService.findCartById(cartId);

            Optional<CartProductDto> resultCartProductDto = cartDto.getCartProductDtos()
                .stream().filter(t -> t.getId().equals(cartProductId))
                .findAny();

            assertNotNull(resultCartProductDto);
            assertEquals(cartProductId, resultCartProductDto.get().getId());
        }

        @Test
        @Order(2)
        @DisplayName("장바구니의 제품 수정")
        void updateCartProduct() throws NotFoundException {
            int fixedCount = 100;
            Member member = memberRepository.findByName("B").get();
            Long memberId = member.getId();
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
            Member member = memberRepository.findByName("B").get();
            Long memberId = member.getId();
            CartDto cartDto = cartService.findCartByMemberId(memberId);
            CartProductDto cartProductDto = cartDto.getCartProductDtos().get(0);

            cartProductService.deleteCartProduct(cartProductDto.getId());

            assertThrows(NotFoundException.class, () -> cartProductService.findCartProductById(cartProductDto.getId()));
        }
    }
}
