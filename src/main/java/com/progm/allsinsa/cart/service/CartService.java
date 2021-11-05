package com.progm.allsinsa.cart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.progm.allsinsa.cart.domain.Cart;
import com.progm.allsinsa.cart.dto.CartConverter;
import com.progm.allsinsa.cart.dto.CartDto;
import com.progm.allsinsa.cart.repository.CartRepository;
import com.progm.allsinsa.member.domain.Member;
import com.progm.allsinsa.member.dto.MemberDto;
import com.progm.allsinsa.member.repository.MemberRepository;
import javassist.NotFoundException;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartConverter cartConverter;
    private final MemberRepository memberRepository;

    public CartService(CartRepository cartRepository,
        CartConverter cartConverter,
        MemberRepository memberRepository) {
        this.cartRepository = cartRepository;
        this.cartConverter = cartConverter;
        this.memberRepository = memberRepository;
    }

    // 맴버가 생성되었을때 장바구니도 생성된다.
    @Transactional
    public CartDto createCart(MemberDto memberDto) throws NotFoundException {
        Member member = memberRepository.findById(memberDto.getId()).orElseThrow(
            () -> new NotFoundException("Member id를 통해 맴버가 조회되지 않습니다. cart를 생성할 수 없습니다."));
        Cart cart = new Cart();
        cart.setMember(member);
        Cart entity = cartRepository.save(cart);
        return cartConverter.convertCartDto(entity);
    }

    // 맴버가 삭제되었을때 장바구니도 삭제된다.
    @Transactional
    public void deleteCart(Long cartId){
        cartRepository.deleteById(cartId);
    }

    // 맴버 id를 이용하여 장바구니 조회
    @Transactional(readOnly = true)
    public CartDto findCartByMemberId(Long memberId) throws NotFoundException {
        return cartRepository.findCartByMemberId(memberId)
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
