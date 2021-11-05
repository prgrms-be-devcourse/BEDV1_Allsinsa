package com.progm.allsinsa.coupon.service;

import com.progm.allsinsa.coupon.domain.CouponInfo;
import com.progm.allsinsa.coupon.dto.CouponConverter;
import com.progm.allsinsa.coupon.dto.CouponInfoDto;
import com.progm.allsinsa.coupon.repository.CouponInfoRepository;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class CouponInfoService {

    private final CouponInfoRepository couponInfoRepository;
    private final CouponConverter couponConverter;

    @Transactional
    public CouponInfoDto createCouponInfo(CouponInfoDto couponInfoDto) {
        CouponInfo couponInfo = couponConverter.convertCouponInfoDto(couponInfoDto);
        couponInfoRepository.save(couponInfo);
        return couponConverter.convertCouponInfo(couponInfo);
    }

    @Transactional(readOnly = true)
    public CouponInfoDto findOne(String couponName) throws NotFoundException {
        return couponConverter.convertCouponInfo(couponInfoRepository.findByCouponName(couponName)
                .orElseThrow(() -> new NotFoundException(couponName + "해당 쿠폰이 존재하지 않습니다.")));
    }

    @Transactional(readOnly = true)
    public Page<CouponInfoDto> findAll(Pageable pageable) {
        return couponInfoRepository.findAll(pageable)
                .map(couponConverter::convertCouponInfo);
    }
}
