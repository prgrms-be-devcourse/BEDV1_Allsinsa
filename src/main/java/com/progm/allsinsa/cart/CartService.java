package com.progm.allsinsa.cart;

import com.progm.allsinsa.cart.cart.Cart;
import com.progm.allsinsa.cart.cart.CartDto;
import com.progm.allsinsa.cart.cart.CartRepository;
import com.progm.allsinsa.cart.cartProduct.CartProduct;
import com.progm.allsinsa.cart.cartProduct.CartProductDto;
import com.progm.allsinsa.cart.cartProduct.CartProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final CartConverter cartConverter;

    public CartService(CartRepository cartRepository,
        CartProductRepository cartProductRepository,
        CartConverter cartConverter) {
        this.cartRepository = cartRepository;
        this.cartProductRepository = cartProductRepository;
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
    @Transactional(readOnly = true)
    public CartDto findCartByMemberId(Long memberId) throws NotFoundException {
        return cartRepository.findCartByMember(memberId)
            .map(cartConverter::convertCartDto)
            .orElseThrow(() -> new NotFoundException("맴버 ID를 이용하여 장바구니를 조회할 수 없습니다."));
    }

    // 장바구니 id를 이용하여 장바구니 조회
    @Transactional(readOnly = true)
    public CartDto findCartById(Long id) throws NotFoundException {
        return cartRepository.findById(id)
            .map(cartConverter::convertCartDto)
            .orElseThrow(() -> new NotFoundException("장바구나 ID를 이용하여 장바구니를 조회할 수 없습니다."));
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
    public void deleteCartProduct(Long cartProductId) throws NotFoundException {
        cartProductRepository.findById(cartProductId)
            .orElseThrow(() -> new NotFoundException("장바구니 물품을 찾을 수 없습니다. 장바구니 물품을 삭제할 수 없습니다."));
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
        CartDto cartDto = findCartByMemberId(memberId);
        return cartProductRepository.findAllByCartId(cartDto.getId())
            .stream()
            .map(cartConverter::convertCartProductDto)
            .collect(Collectors.toList());

    }
}
