package com.progm.allsinsa.coupon;

import com.progm.allsinsa.coupon.domain.CouponInfo;
import com.progm.allsinsa.coupon.domain.CouponType;
import com.progm.allsinsa.coupon.dto.CouponConverter;
import com.progm.allsinsa.coupon.dto.CouponDto;
import com.progm.allsinsa.coupon.service.CouponService;
import com.progm.allsinsa.member.domain.Member;
import com.progm.allsinsa.member.dto.MemberConverter;
import com.progm.allsinsa.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@Transactional
@SpringBootTest
public class CouponServiceTest {
    @Autowired
    CouponService couponService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberConverter memberConverter;

    @Autowired
    CouponConverter couponConverter;

    @Test
    public void test() {
        Member sezikim = new Member("test1@gmail.com", "aAbB1234", "sezikim");
        memberRepository.save(sezikim);

        CouponInfo couponInfo = new CouponInfo("11월 무신사 정기 할인 쿠폰", CouponType.PERCENT_DISCOUNT_COUPON, 7);
        CouponDto couponDto = new CouponDto(memberConverter.convertMemberDto(sezikim),
                couponConverter.convertCouponInfo(couponInfo),
                LocalDateTime.of(2021,12,31,0,0));
        CouponDto retrievedCouponDto = couponService.issueCoupon(couponDto);

        assertThat(retrievedCouponDto, samePropertyValuesAs(couponDto));
    }
}
