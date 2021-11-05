package com.progm.allsinsa.coupon;

import com.progm.allsinsa.coupon.domain.Coupon;
import com.progm.allsinsa.coupon.domain.CouponHistory;
import com.progm.allsinsa.coupon.domain.CouponInfo;
import com.progm.allsinsa.coupon.domain.CouponType;
import com.progm.allsinsa.member.domain.Member;
import com.progm.allsinsa.product.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
public class CouponDomainTest {

    @Autowired
    private EntityManagerFactory emf;

    @Test
    public void failCreateCouponInfoTest() {
        assertThrows(IllegalArgumentException.class,
                () -> new CouponInfo("test coupon1", CouponType.PERCENT_DISCOUNT_COUPON, -10));
        assertThrows(IllegalArgumentException.class,
                () -> new CouponInfo("test coupon2", CouponType.PERCENT_DISCOUNT_COUPON, 60));
        assertThrows(IllegalArgumentException.class,
                () -> new CouponInfo("test coupon3", CouponType.FIXED_AMOUNT_COUPON, 20000));
        assertThrows(IllegalArgumentException.class,
                () -> new CouponInfo("test coupon4", CouponType.FIXED_AMOUNT_COUPON, -3000));
    }

    @Test
    public void createCouponInfoTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // given
        CouponInfo couponInfo1 = new CouponInfo("10월 무신사 정기 할인 쿠폰", CouponType.PERCENT_DISCOUNT_COUPON, 7);
        CouponInfo couponInfo2 = new CouponInfo("생일 쿠폰", CouponType.FIXED_AMOUNT_COUPON, 10000);
        entityManager.persist(couponInfo1);
        entityManager.persist(couponInfo2);

        // when
        CouponInfo retrievedCouponInfo1 = entityManager.find(CouponInfo.class, couponInfo1.getId());
        CouponInfo retrievedCouponInfo2 = entityManager.find(CouponInfo.class, couponInfo2.getId());

        // then
        assertAll(() -> {
            assertThat(retrievedCouponInfo1.getCouponName()).isEqualTo(couponInfo1.getCouponName());
            assertThat(retrievedCouponInfo1.getCouponType()).isEqualTo(couponInfo1.getCouponType());
            assertThat(retrievedCouponInfo1.getDiscountValue()).isEqualTo(couponInfo1.getDiscountValue());
            assertThat(retrievedCouponInfo2.getCouponName()).isEqualTo(couponInfo2.getCouponName());
            assertThat(retrievedCouponInfo2.getCouponType()).isEqualTo(couponInfo2.getCouponType());
            assertThat(retrievedCouponInfo2.getDiscountValue()).isEqualTo(couponInfo2.getDiscountValue());
        });
        transaction.commit();
    }

    @Test
    public void issueCouponTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // given
        Member sezikim = new Member("test1@gmail.com", "aAbB1234", "sezikim");
        CouponInfo couponInfo = new CouponInfo("11월 무신사 정기 할인 쿠폰", CouponType.PERCENT_DISCOUNT_COUPON, 7);
        Coupon coupon = new Coupon(sezikim, couponInfo, LocalDateTime.of(2021, 1, 30, 0, 0));
        entityManager.persist(sezikim);
        entityManager.persist(coupon);

        // when
        Coupon retrievedCoupon = entityManager.find(Coupon.class, coupon.getId());

        // then
        assertAll(() -> {
            assertThat(retrievedCoupon.getCouponInfo()).isEqualTo(couponInfo);
            assertThat(retrievedCoupon.getMember()).isEqualTo(sezikim);
            assertThat(retrievedCoupon.getExpiredAt()).isEqualTo(coupon.getExpiredAt());
            assertThat(retrievedCoupon.getUsable()).isTrue();
        });
        transaction.commit();
    }

    @Test
    public void useCouponTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // given
        Product product = new Product("청",
                30000,
                "바지",
                "상태..?",
                "https://image.msscdn.net/images/goods_img/20191114/1224916/1224916_2_500.jpg",
                "https://image.msscdn.net/images/goods_img/20191114/1224916/1224916_2_500.jpg",
                1L);
        Member sezikim = new Member("test2@gmail.com", "aAbB1234", "sezikim");
        CouponInfo couponInfo = new CouponInfo("12월 무신사 정기 할인 쿠폰", CouponType.PERCENT_DISCOUNT_COUPON, 7);
        Coupon coupon = new Coupon(sezikim, couponInfo, LocalDateTime.of(2021, 1, 30, 0, 0));
        entityManager.persist(product);
        entityManager.persist(sezikim);
        entityManager.persist(coupon);

        // when
        Coupon retrievedCoupon = entityManager.find(Coupon.class, coupon.getId());
        CouponHistory couponHistory = retrievedCoupon.useCoupon(product);
        entityManager.persist(couponHistory);

        CouponHistory retrievedCouponHistory = entityManager.find(CouponHistory.class, couponHistory.getId());

        // then
        assertAll(() -> {
            assertThat(coupon.getUsable()).isFalse();
            assertThat(retrievedCouponHistory.getProduct()).isEqualTo(product);
            assertThat(retrievedCouponHistory.getCoupon()).isEqualTo(coupon);
        });
        transaction.commit();
    }
}
