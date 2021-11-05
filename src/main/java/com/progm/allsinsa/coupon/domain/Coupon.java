package com.progm.allsinsa.coupon.domain;

import com.progm.allsinsa.member.domain.Member;
import com.progm.allsinsa.product.domain.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_info_id")
    private CouponInfo couponInfo;

    private Boolean usable;

    @CreatedDate
    private LocalDateTime issuedAt;

    private LocalDateTime expiredAt;

    public Coupon(Member member, CouponInfo couponInfo, LocalDateTime expiredAt) {
        this.member = member;
        this.usable = true;
        this.expiredAt = expiredAt;
        changeCouponInfo(couponInfo);
    }

    public CouponHistory useCoupon(Product product) {
        this.usable = false;
        return new CouponHistory(this, product, getDiscountAmount(product));
    }

    private int getDiscountAmount(Product product) {
        if (couponInfo.getCouponType() == CouponType.FIXED_AMOUNT_COUPON)
            return couponInfo.getDiscountValue();

        return (int)((100 - couponInfo.getDiscountValue()) * (double)(product.getPrice()) / 100);
    }

    private void changeCouponInfo(CouponInfo couponInfo) {
        this.couponInfo = couponInfo;
        couponInfo.getCoupons().add(this);
    }
}
