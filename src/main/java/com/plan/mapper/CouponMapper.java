package com.plan.mapper;

import com.plan.dto.CouponDto;
import com.plan.entity.Coupon;
import org.springframework.stereotype.Component;

@Component
public class CouponMapper {

    public Coupon toEntity(CouponDto dto) {
        if (dto == null) {
            return null;
        }

        Coupon coupon = new Coupon();
        coupon.setId(dto.getId());
        coupon.setCouponCode(dto.getCouponCode());
        coupon.setDiscountPercent(dto.getDiscountPercent());
        coupon.setStatus(dto.getStatus() != null ? dto.getStatus() : true);  // default true if null
        coupon.setCreatedAt(dto.getCreatedAt());
        coupon.setUpdatedAt(dto.getUpdatedAt());

        return coupon;
    }

    public CouponDto toDto(Coupon entity) {
        if (entity == null) {
            return null;
        }

        CouponDto dto = new CouponDto();
        dto.setId(entity.getId());
        dto.setCouponCode(entity.getCouponCode());
        dto.setDiscountPercent(entity.getDiscountPercent());
        dto.setStatus(entity.isStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }
}
