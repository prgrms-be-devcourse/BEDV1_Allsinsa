package com.progm.allsinsa.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberDto {
    Long id;
    String email;
    String password;
    String name;
}
