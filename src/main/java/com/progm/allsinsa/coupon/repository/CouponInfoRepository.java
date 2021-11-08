package com.progm.allsinsa.coupon.repository;

import com.progm.allsinsa.coupon.domain.CouponInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponInfoRepository extends JpaRepository<CouponInfo, Long> {
    Optional<CouponInfo> findByCouponName(String couponName);
}
