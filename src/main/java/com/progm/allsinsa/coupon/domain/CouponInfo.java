package com.progm.allsinsa.coupon.domain;

import com.progm.allsinsa.global.domain.BaseTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CouponInfo extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String couponName;

    @Enumerated(value = EnumType.STRING)
    private CouponType couponType;

    @Column(nullable = false)
    private Integer discountValue;

    @OneToMany(mappedBy = "couponInfo", fetch = FetchType.LAZY)
    private List<Coupon> coupons = new ArrayList<>();

    public CouponInfo(String couponName, CouponType couponType, Integer discountValue) {
        this.couponName = couponName;
        this.couponType = couponType;
        if (discountValue < 0)
            throw new IllegalArgumentException("할인금액은 음수가 될 수 없습니다.");
        if (discountValue > 10000 && couponType == CouponType.FIXED_AMOUNT_COUPON)
            throw new IllegalArgumentException("10000원 이하의 할인금액을 갖는 쿠폰만 생성할 수 있습니다.");
        if (discountValue > 49 && couponType == CouponType.PERCENT_DISCOUNT_COUPON)
            throw new IllegalArgumentException("50% 미만의 할인율을 갖는 쿠폰만 생성할 수 있습니다.");
        this.discountValue = discountValue;
    }
}
