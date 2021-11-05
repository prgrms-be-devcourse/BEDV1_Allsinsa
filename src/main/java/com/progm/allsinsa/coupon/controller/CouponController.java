package com.progm.allsinsa.coupon.controller;

import com.progm.allsinsa.coupon.dto.CouponDto;
import com.progm.allsinsa.coupon.dto.CouponInfoDto;
import com.progm.allsinsa.coupon.service.CouponInfoService;
import com.progm.allsinsa.coupon.service.CouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
public class CouponController {
    private final CouponInfoService couponInfoService;
    private final CouponService couponService;

    public CouponController(CouponInfoService couponInfoService, CouponService couponService) {
        this.couponInfoService = couponInfoService;
        this.couponService = couponService;
    }

    @PostMapping("/coupon-info")
    public ResponseEntity<CouponInfoDto> createCouponInfo(@RequestBody CouponInfoDto couponInfoDto) {
        CouponInfoDto responseBody = couponInfoService.createCouponInfo(couponInfoDto);
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/coupons")
    public ResponseEntity<CouponDto> issueCoupon(@RequestBody CouponDto couponDto) {
        CouponDto responseBody = couponService.issueCoupon(couponDto);
        return ResponseEntity.ok(responseBody);
    }

}
