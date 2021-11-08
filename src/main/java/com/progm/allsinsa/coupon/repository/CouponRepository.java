package com.progm.allsinsa.coupon.repository;

import com.progm.allsinsa.coupon.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
