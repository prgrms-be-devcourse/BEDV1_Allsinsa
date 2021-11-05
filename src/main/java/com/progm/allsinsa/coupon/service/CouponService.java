package com.progm.allsinsa.coupon.service;

import com.progm.allsinsa.coupon.domain.Coupon;
import com.progm.allsinsa.coupon.dto.CouponConverter;
import com.progm.allsinsa.coupon.dto.CouponDto;
import com.progm.allsinsa.coupon.repository.CouponRepository;
import com.progm.allsinsa.member.domain.Member;
import com.progm.allsinsa.member.repository.MemberRepository;
import org.springframework.stereotype.Service;


@Service
public class CouponService {
    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;
    private final CouponConverter couponConverter;

    public CouponService(CouponRepository couponRepository, MemberRepository memberRepository, CouponConverter couponConverter) {
        this.couponRepository = couponRepository;
        this.memberRepository = memberRepository;
        this.couponConverter = couponConverter;
    }

    public CouponDto issueCoupon(CouponDto couponDto) {
        Member member = memberRepository.findByEmail(couponDto.memberDto().getEmail()).get();
        Coupon savedCoupon = couponRepository.save(
                new Coupon(member,
                        couponConverter.convertCouponInfoDto(couponDto.couponInfoDto()),
                        couponDto.expiredAt()));
        CouponDto savedCouponDto = couponConverter.convertCoupon(savedCoupon);
        return savedCouponDto;
    }
}
