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
    public void deleteMember(Long memberId) throws NotFoundException {
        // delete cart
        CartDto cart = cartService.findCartByMemberId(memberId);
        cartService.deleteCart(cart.getId());
        // delete member
        memberRepository.deleteById(memberId);
    }

    @Transactional(readOnly = true)
    public MemberDto findById(Long memberId) throws NotFoundException {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("Member id??? ????????? ????????? ??? ????????????."));
        return memberConverter.convertMemberDto(member);
    }

    @Transactional(readOnly = true)
    public MemberDto findByEmail(String email) throws NotFoundException {
        Email inputEmail = new Email(email); // validate check
        Member member = memberRepository.findByEmail(inputEmail.getEmail())
                .orElseThrow(() -> new NotFoundException("Member email??? ????????? ????????? ??? ????????????."));
        return memberConverter.convertMemberDto(member);
    }

    @Transactional
    public MemberDto updateMember(MemberDto memberDto) throws NotFoundException {
        Member member = memberRepository.findById(memberDto.getId())
                .orElseThrow(() -> new NotFoundException("Member id??? ????????? ????????? ??? ????????????. ??????????????? ????????? ??? ????????????."));

        member.updateMember(memberDto.getName(), memberDto.getPassword());
        return memberConverter.convertMemberDto(member);
    }

    @Transactional(readOnly = true)
    public MemberDto login(MemberDto memberDto) throws NotFoundException, IllegalAccessException {
        log.trace("Email validate");
        Email inputEmail = new Email(memberDto.getEmail()); // validate check

        Member member = memberRepository.findByEmail(inputEmail.getEmail())
                .orElseThrow(
                        () -> new NotFoundException("Member email??? ????????? ????????? ??? ????????????. ???????????? ??? ??? ????????????."));

        Password inputPassword = new Password(memberDto.getPassword()); //validate check

        if (!member.loginCheck(inputPassword.getPassword()))
            throw new IllegalAccessException("Member password??? ???????????? ????????????. ????????? ??? ??? ????????????.");

        return memberDto;
    }

}
