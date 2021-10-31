package com.progm.allsinsa.cart.service;

import com.progm.allsinsa.cart.dto.CartConverter;
import com.progm.allsinsa.cart.domain.Cart;
import com.progm.allsinsa.cart.domain.CartProduct;
import com.progm.allsinsa.cart.dto.CartDto;
import com.progm.allsinsa.cart.dto.CartProductDto;
import com.progm.allsinsa.cart.repository.CartProductRepository;
import com.progm.allsinsa.cart.repository.CartRepository;
import java.util.List;
import java.util.stream.Collectors;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartProductService {
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final CartConverter cartConverter;
    private final CartService cartService;

    public CartProductService(CartRepository cartRepository,
        CartProductRepository cartProductRepository,
        CartConverter cartConverter, CartService cartService) {
        this.cartRepository = cartRepository;
        this.cartProductRepository = cartProductRepository;
        this.cartConverter = cartConverter;
        this.cartService = cartService;
    }

    /* CartProduct */
    // 장바구니 제품 추가
    @Transactional
    public CartProductDto saveCartProduct(Long cartId, CartProductDto cartProductDto)
        throws NotFoundException {
        Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new NotFoundException("장바구니 ID를 이용하여 상품을 장바구니에 추가할 수 없습니다."));
        CartProduct cartProduct = new CartProduct(cartProductDto.getCount(), cartProductDto.getProductOptionDto());
        cartProduct.setCart(cart);
        CartProduct entity = cartProductRepository.save(cartProduct);

        return cartConverter.convertCartProductDto(entity);
    }

    // 장바구니 제품 수정
    @Transactional
    public CartProductDto updateCartProduct(CartProductDto cartProductDto)
        throws NotFoundException {
        CartProduct entity = cartProductRepository.findById(cartProductDto.getId())
            .orElseThrow(() -> new NotFoundException("장바구니 물품을 찾을 수 없습니다. 장바구니 물품을 수정할 수 없습니다."));
        entity.setCount(cartProductDto.getCount());

        return cartConverter.convertCartProductDto(entity);
    }

    // 장바구니 제품 삭제
    @Transactional
    public void deleteCartProduct(Long cartProductId) {
        cartProductRepository.deleteById(cartProductId);
    }

    // 장바구니 제품 개별 조회
    @Transactional(readOnly = true)
    public CartProductDto findCartProductById(Long cartProductId) throws NotFoundException {
        CartProduct cartProduct = cartProductRepository.findById(cartProductId)
            .orElseThrow(() -> new NotFoundException("장바구니 물품을 찾을 수 없습니다. 장바구니 물품을 조회할 수 없습니다."));
        return cartConverter.convertCartProductDto(cartProduct);
    }

    // 장바구니 제품 전체 조회
    @Transactional(readOnly = true)
    public List<CartProductDto> findCartProductAll(Long memberId)
        throws NotFoundException {
        CartDto cartDto = cartService.findCartByMemberId(memberId);
        return cartProductRepository.findAllByCartId(cartDto.getId())
            .stream()
            .map(cartConverter::convertCartProductDto)
            .collect(Collectors.toList());
    }
}
