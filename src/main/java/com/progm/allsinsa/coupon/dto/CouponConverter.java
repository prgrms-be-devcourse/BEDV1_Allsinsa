package com.progm.allsinsa.coupon.dto;

import com.progm.allsinsa.coupon.domain.Coupon;
import com.progm.allsinsa.coupon.domain.CouponInfo;
import com.progm.allsinsa.coupon.domain.CouponType;
import com.progm.allsinsa.member.dto.MemberConverter;
import org.springframework.stereotype.Component;

@Component
public class CouponConverter {

    private final MemberConverter memberConverter;

    public CouponConverter(MemberConverter memberConverter) {
        this.memberConverter = memberConverter;
    }

    public CouponInfoDto convertCouponInfo(CouponInfo couponInfo) {
        return new CouponInfoDto(couponInfo.getCouponName(),
                couponInfo.getCouponType().name(),
                couponInfo.getDiscountValue());
    }

    public CouponInfo convertCouponInfoDto(CouponInfoDto couponInfoDto) {
        return new CouponInfo(couponInfoDto.couponName(),
                CouponType.valueOf(couponInfoDto.couponType()),
                couponInfoDto.discountValue());
    }

    public CouponDto convertCoupon(Coupon coupon) {
        return new CouponDto(memberConverter.convertMemberDto(coupon.getMember()),
                convertCouponInfo(coupon.getCouponInfo()),
                coupon.getExpiredAt());
    }
}
