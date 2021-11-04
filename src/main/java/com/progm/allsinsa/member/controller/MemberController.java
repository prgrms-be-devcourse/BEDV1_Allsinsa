package com.progm.allsinsa.member.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.progm.allsinsa.member.domain.Email;
import com.progm.allsinsa.member.dto.MemberDto;
import com.progm.allsinsa.member.service.MemberService;
import javassist.NotFoundException;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<MemberDto> createMember(@RequestBody MemberDto memberDto)
            throws NotFoundException {
        MemberDto dto = memberService.createMember(memberDto);
        return ResponseEntity.created(
                URI.create("/api/v1/members/" + dto.getId())
        ).body(dto);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDto> readMember(@PathVariable Long memberId)
            throws NotFoundException {
        MemberDto dto = memberService.findById(memberId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<MemberDto> getMember(@RequestParam String email)
            throws NotFoundException {
        Email validatedEmail = new Email(email);
        MemberDto dto = memberService.findByEmail(validatedEmail.getEmail());
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMember(@RequestBody MemberDto memberDto)
            throws NotFoundException {
        memberService.deleteMember(memberDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<MemberDto> updateMember(@RequestBody MemberDto memberDto)
            throws NotFoundException {
        MemberDto dto = memberService.updateMember(memberDto);
        return ResponseEntity.ok(dto);
    }

}
