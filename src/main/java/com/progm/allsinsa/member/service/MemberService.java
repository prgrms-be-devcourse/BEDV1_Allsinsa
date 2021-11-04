package com.progm.allsinsa.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.progm.allsinsa.cart.dto.CartDto;
import com.progm.allsinsa.cart.service.CartService;
import com.progm.allsinsa.member.domain.Email;
import com.progm.allsinsa.member.domain.Member;
import com.progm.allsinsa.member.domain.Password;
import com.progm.allsinsa.member.dto.MemberConverter;
import com.progm.allsinsa.member.dto.MemberDto;
import com.progm.allsinsa.member.repository.MemberRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberConverter memberConverter;
    private final CartService cartService;

    public MemberService(MemberRepository memberRepository,
            MemberConverter memberConverter, CartService cartService) {
        this.memberRepository = memberRepository;
        this.memberConverter = memberConverter;
        this.cartService = cartService;
    }

    @Transactional
    public MemberDto createMember(MemberDto memberDto) throws NotFoundException {
        Member member = new Member(memberDto.getEmail(), memberDto.getPassword(), memberDto.getName());
        Member entity = memberRepository.save(member);

        // create cart
        cartService.createCart(memberConverter.convertMemberDto(entity));
        return memberConverter.convertMemberDto(entity);
    }

    @Transactional
    public void deleteMember(MemberDto memberDto) throws NotFoundException {
        // delete cart
        CartDto cart = cartService.findCartByMemberId(memberDto.getId());
        cartService.deleteCart(cart.getId());
        // delete member
        memberRepository.deleteById(memberDto.getId());
    }

    @Transactional(readOnly = true)
    public MemberDto findById(Long memberId) throws NotFoundException {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("Member id로 회원을 검색할 수 없습니다."));
        return memberConverter.convertMemberDto(member);
    }

    @Transactional(readOnly = true)
    public MemberDto findByEmail(String email) throws NotFoundException {
        Email inputEmail = new Email(email); // validate check
        Member member = memberRepository.findByEmail(inputEmail.getEmail())
                .orElseThrow(() -> new NotFoundException("Member email로 회원을 검색할 수 없습니다."));
        return memberConverter.convertMemberDto(member);
    }

    @Transactional
    public MemberDto updateMember(MemberDto memberDto) throws NotFoundException {
        Member member = memberRepository.findById(memberDto.getId())
                .orElseThrow(() -> new NotFoundException("Member id로 회원을 검색할 수 없습니다. 회원정보를 수정할 수 없습니다."));

        member.updateMember(memberDto.getName(), memberDto.getPassword());
        return memberConverter.convertMemberDto(member);
    }

    @Transactional(readOnly = true)
    public MemberDto login(MemberDto memberDto) throws NotFoundException, IllegalAccessException {
        log.trace("Email validate");
        Email inputEmail = new Email(memberDto.getEmail()); // validate check

        Member member = memberRepository.findByEmail(inputEmail.getEmail())
                .orElseThrow(
                        () -> new NotFoundException("Member email로 회원을 검색할 수 없습니다. 로그인을 할 수 없습니다."));

        Password inputPassword = new Password(memberDto.getPassword()); //validate check

        if (!member.loginCheck(inputPassword.getPassword()))
            throw new IllegalAccessException("Member password가 올바르지 않습니다. 로그인 할 수 없습니다.");

        return memberDto;
    }

}
