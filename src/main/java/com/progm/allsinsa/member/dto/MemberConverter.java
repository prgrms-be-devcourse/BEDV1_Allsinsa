package com.progm.allsinsa.member.dto;

import com.progm.allsinsa.member.domain.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {
    public MemberDto convertMemberDto(Member member) {
        return MemberDto.builder()
            .id(member.getId())
            .password(member.getPassword())
            .email(member.getEmail())
            .name(member.getName())
            .build();
    }
}
