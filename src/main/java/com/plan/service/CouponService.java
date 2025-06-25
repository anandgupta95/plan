package com.plan.service;

import com.plan.dto.ApiPageResponse;
import com.plan.dto.ApiResponse;
import com.plan.dto.CouponDto;
import com.plan.entity.Coupon;
import com.plan.exception.ResourceNotFoundException;
import com.plan.mapper.CouponMapper;
import com.plan.repository.CouponRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;

    public CouponService(CouponRepository couponRepository, CouponMapper couponMapper) {
        this.couponRepository = couponRepository;
        this.couponMapper = couponMapper;
    }

    // Create coupon
    public ApiResponse<CouponDto> createCoupon(CouponDto dto) {
        Coupon coupon = couponMapper.toEntity(dto);
        Coupon savedCoupon = couponRepository.save(coupon);
        return new ApiResponse<>(true, "Coupon created successfully!", couponMapper.toDto(savedCoupon));
    }

    public ApiPageResponse<CouponDto> getAllCoupons(Pageable pageable, Integer week, Integer month, Integer year, LocalDate startDate, LocalDate endDate) {

        // Default: no filtering
        LocalDateTime fromDateTime = null;
        LocalDateTime toDateTime = null;

        LocalDate today = LocalDate.now();



        Page<Coupon> couponPage;

        if (fromDateTime != null && toDateTime != null) {
            couponPage = couponRepository.findByCreatedAtBetween(fromDateTime, toDateTime, pageable);
        } else {
            couponPage = couponRepository.findAll(pageable);
        }

        Page<CouponDto> dtoPage = couponPage.map(couponMapper::toDto);
        return ApiPageResponse.fromPage(dtoPage, "Coupons fetched successfully");
    }


    // Get coupon by ID
    public ApiResponse<CouponDto> getCouponById(Long id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon not found with ID: " + id));
        return new ApiResponse<>(true, "Coupon fetched successfully!", couponMapper.toDto(coupon));
    }

    // Update coupon
    public ApiResponse<CouponDto> updateCoupon(Long id, CouponDto dto) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon not found with ID: " + id));

        coupon.setCouponCode(dto.getCouponCode());
        coupon.setDiscountPercent(dto.getDiscountPercent());
        coupon.setStatus(dto.getStatus());

        Coupon updatedCoupon = couponRepository.save(coupon);
        return new ApiResponse<>(true, "Coupon updated successfully!", couponMapper.toDto(updatedCoupon));
    }

    // Delete coupon
    public ApiResponse<String> deleteCoupon(Long id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon not found with ID: " + id));
        couponRepository.delete(coupon);
        return new ApiResponse<>(true, "Coupon deleted successfully!", null);
    }


}
