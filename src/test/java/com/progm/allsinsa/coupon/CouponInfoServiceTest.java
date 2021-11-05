package com.progm.allsinsa.coupon;

import com.progm.allsinsa.coupon.domain.CouponInfo;
import com.progm.allsinsa.coupon.dto.CouponConverter;
import com.progm.allsinsa.coupon.dto.CouponInfoDto;
import com.progm.allsinsa.coupon.repository.CouponInfoRepository;
import com.progm.allsinsa.coupon.service.CouponInfoService;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@Transactional
@SpringBootTest
public class CouponInfoServiceTest {

    @Autowired
    private CouponInfoService couponInfoService;

    @Autowired
    private CouponInfoRepository couponInfoRepository;

    @Autowired
    private CouponConverter couponConverter;

    @Test
    public void couponInfoCreateTest() {
        // given
        CouponInfoDto percentDiscountCoupon = new CouponInfoDto(
                "현준이 10월 쿠폰",
                "PERCENT_DISCOUNT_COUPON",
                30);

        CouponInfoDto couponInfoDto = couponInfoService.createCouponInfo(percentDiscountCoupon);

        // when
        CouponInfo retrievedCouponInfo = couponInfoRepository.findByCouponName(couponInfoDto.couponName()).get();

        // then
        assertThat(couponConverter.convertCouponInfo(retrievedCouponInfo), samePropertyValuesAs(couponInfoDto));
    }

    @Test
    public void findOneTest() throws NotFoundException {
        // given
        CouponInfoDto createBaseAmountCouponDto = new CouponInfoDto(
                "기본 할인금액 쿠폰",
                "FIXED_AMOUNT_COUPON",
                3000);
        CouponInfoDto baseAmountCouponDto = couponInfoService.createCouponInfo(createBaseAmountCouponDto);

        // when
        CouponInfoDto one = couponInfoService.findOne(baseAmountCouponDto.couponName());

        // then
        assertThat(one, samePropertyValuesAs(baseAmountCouponDto));
    }

    @Test
    public void findAllTest() {
        // given
        CouponInfoDto createBasePercentCouponDto = new CouponInfoDto(
                "기본 할인율 쿠폰",
                "PERCENT_DISCOUNT_COUPON",
                30);
        CouponInfoDto percentDiscountCoupon = new CouponInfoDto(
                "현준이 11월 쿠폰",
                "PERCENT_DISCOUNT_COUPON",
                30);

        CouponInfoDto basePercentCouponDto = couponInfoService.createCouponInfo(createBasePercentCouponDto);
        CouponInfoDto couponInfoDto = couponInfoService.createCouponInfo(percentDiscountCoupon);

        // when
        Page<CouponInfoDto> all = couponInfoService.findAll(PageRequest.of(0, 10));

        // then
        assertThat(all.getContent().size(), is(2));
        assertThat(all.getContent().
                        containsAll(List.of(basePercentCouponDto, couponInfoDto)),
                is(true));
    }
}
