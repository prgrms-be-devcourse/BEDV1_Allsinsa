package com.progm.allsinsa.member.service;

import static org.junit.jupiter.api.Assertions.*;

import com.progm.allsinsa.member.domain.Member;
import com.progm.allsinsa.member.dto.MemberConverter;
import com.progm.allsinsa.member.dto.MemberDto;
import com.progm.allsinsa.member.repository.MemberRepository;
import java.util.Optional;
import javassist.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("MemberServiceTest")
@SpringBootTest
@TestMethodOrder(value = OrderAnnotation.class)
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberConverter memberConverter;

    @Autowired
    MemberRepository memberRepository;

    Logger log = LoggerFactory.getLogger(this.getClass());

    String memberName = "memberA";
    String memberEmail = "test@abc.com";
    String memberPassword = "123abcDEF!";

    @Test
    @Order(1)
    @DisplayName("맴버 생성 테스트")
    void createMemberTest() throws NotFoundException {
        MemberDto createdMemberDto = MemberDto.builder()
            .name(memberName)
            .email(memberEmail)
            .password(memberPassword)
            .build();
        MemberDto memberDto = memberService.createMember(createdMemberDto);

        Optional<Member> byId = memberRepository.findById(memberDto.getId());
        assertNotNull(byId);
        Member resultMember = byId.get();

        assertAll(
            () -> assertEquals(memberDto.getId(), resultMember.getId()),
            () -> assertEquals(memberEmail, resultMember.getEmail()),
            () -> assertEquals(memberPassword, resultMember.getPassword())
        );
    }

    @Test
    @Order(2)
    void findByEmailTest() {
        MemberDto memberDto = assertDoesNotThrow(() -> memberService.findByEmail(memberEmail));

        assertAll(
            () -> assertEquals(memberEmail, memberDto.getEmail()),
            () -> assertEquals(memberPassword, memberDto.getPassword())
        );
    }

    @Test
    @Order(3)
    void findByIdTest() {
        MemberDto dto = assertDoesNotThrow(() -> memberService.findByEmail(memberEmail));
        MemberDto memberDto = assertDoesNotThrow(() -> memberService.findById(dto.getId()));
        assertAll(
            () -> assertEquals(memberEmail, memberDto.getEmail()),
            () -> assertEquals(memberPassword, memberDto.getPassword())
        );
    }

    @Test
    @Order(4)
    void loginTest() {
        MemberDto dto = assertDoesNotThrow(() -> memberService.findByEmail(memberEmail));
        assertDoesNotThrow(() -> memberService.login(dto));
    }

    @Test
    @Order(5)
    void updateMemberTest() {
        String fixedMemberName = "memberB";
        String fixedMemberPassword = "fixedPW123abcDEF!";
        MemberDto dto = assertDoesNotThrow(() -> memberService.findByEmail(memberEmail));
        MemberDto fixedMemberDto = new MemberDto(dto.getId(), dto.getEmail(), fixedMemberPassword,
            fixedMemberName);
        MemberDto resultMemberDto = assertDoesNotThrow(() -> memberService.updateMember(fixedMemberDto));

        assertAll(
            () -> assertEquals(dto.getId(), resultMemberDto.getId()),
            () -> assertEquals(memberEmail, resultMemberDto.getEmail()),
            () -> assertEquals(fixedMemberPassword, resultMemberDto.getPassword()),
            () -> assertEquals(fixedMemberName, resultMemberDto.getName())
        );
    }

    @Test
    @Order(6)
    void deleteMemberTest() {
        MemberDto dto = assertDoesNotThrow(() -> memberService.findByEmail(memberEmail));
        assertDoesNotThrow(() -> memberService.deleteMember(dto));
    }
}