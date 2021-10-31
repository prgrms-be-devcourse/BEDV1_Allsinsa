package com.progm.allsinsa.cart.service;

import com.progm.allsinsa.cart.dto.CartConverter;
import com.progm.allsinsa.cart.domain.Cart;
import com.progm.allsinsa.cart.dto.CartDto;
import com.progm.allsinsa.cart.repository.CartRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartConverter cartConverter;

    public CartService(CartRepository cartRepository,
        CartConverter cartConverter) {
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
    public void deleteCart(Long cartId){
        cartRepository.deleteById(cartId);
    }

    // 맴버 id를 이용하여 장바구니 조회
    @Transactional(readOnly = true)
    public CartDto findCartByMemberId(Long memberId) throws NotFoundException {
        return cartRepository.findCartByMember(memberId)
            .map(cartConverter::convertCartDto)
            .orElseThrow(() -> new NotFoundException("맴버 ID를 이용하여 장바구니 정보를 조회할 수 없습니다."));
    }

    // 장바구니 id를 이용하여 장바구니 조회
    @Transactional(readOnly = true)
    public CartDto findCartById(Long id) throws NotFoundException {
        return cartRepository.findById(id)
            .map(cartConverter::convertCartDto)
            .orElseThrow(() -> new NotFoundException("장바구나 ID를 이용하여 장바구니 정보를 조회할 수 없습니다."));
    }
}
