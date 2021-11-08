package com.progm.allsinsa.coupon.dto;

import com.progm.allsinsa.member.dto.MemberDto;

import java.time.LocalDateTime;

public record CouponDto(MemberDto memberDto, CouponInfoDto couponInfoDto, LocalDateTime expiredAt) {
}
