package com.progm.allsinsa.cart;

import com.progm.allsinsa.cart.cartProduct.CartProduct;
import com.progm.allsinsa.cart.cartProduct.CartProductDto;
import java.util.Optional;
import javassist.NotFoundException;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartConverter cartConverter;

    public CartService(CartRepository cartRepository, CartConverter cartConverter) {
        this.cartRepository = cartRepository;
        this.cartConverter = cartConverter;
    }

    /* Cart */

    // TODO : input MemberDto return cart.getId()
    // 맴버가 생성되었을때 장바구니도 생성된다.
    @Transactional
    public Long createCart(Long memberId) {
        Cart cart = new Cart(memberId);
        Cart entity = cartRepository.save(cart);
        return entity.getId();
    }

    // 맴버가 삭제되었을때 장바구니도 삭제된다.
    @Transactional
    public void deleteCart(Long cartId) throws NotFoundException {
        Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new NotFoundException("장바구니 ID를 찾을 수가 없어 장바구니를 삭제할 수 없습니다."));
        cartRepository.delete(cart);
    }

    // 맴버 id를 이용하여 장바구니 조회
    // TODO : readOnly=true 적용이 안됨
    @Transactional
    public CartDto findCartByMemberId(Long memberId) throws NotFoundException {
        return cartRepository.findCartByMember(memberId)
            .map(cartConverter::convertCartDto)
            .orElseThrow(() -> new NotFoundException("맴버 ID를 이용하여 장바구니를 조회할 수 없습니다."));
    }

    // 장바구니 id를 이용하여 장바구니 조회
    @Transactional
    public CartDto findCartById(Long id) throws NotFoundException {
        return cartRepository.findById(id)
            .map(cartConverter::convertCartDto)
            .orElseThrow(() -> new NotFoundException("장바구나 ID를 이용하여 장바구니를 조회할 수 없습니다."));
    }

    /* CartProduct */

    // 장바구니 제품 목록 읽기 - 장바구니 id를 이용하여 장바구니 조회로 대체
    // 장바구니 제품 추가
    @Transactional
    public Long saveCartProduct(Long cartId, CartProductDto cartProductDto)
        throws NotFoundException {
        Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new NotFoundException("장바구니 ID를 이용하여 상품을 장바구니에 추가할 수 없습니다."));
        CartProduct cartProduct = new CartProduct(cartProductDto.getId(),
            cartProductDto.getCount(), cartProductDto.getProductOptionDto());
        cartProduct.setCart(cart);

        return cartProduct.getId();
    }

    // 장바구니 제품 수정


    // 장바구니 제품 삭제

}
