package com.plan.controller;

import com.plan.dto.ApiPageResponse;
import com.plan.dto.ApiResponse;
import com.plan.dto.CouponDto;
import com.plan.service.CouponService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    // Create Coupon
    @PostMapping("/create-coupon")
    public ResponseEntity<ApiResponse<CouponDto>> createCoupon(@RequestBody CouponDto dto) {
        return ResponseEntity.ok(couponService.createCoupon(dto));
    }

    // Get All Coupons (Pagination, Sorting)
    @GetMapping("/all")
    public ResponseEntity<ApiPageResponse<CouponDto>> getAllCoupons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(required = false) Integer week,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        ApiPageResponse<CouponDto> coupons = couponService.getAllCoupons(pageable, week, month, year,  startDate, endDate);
        return ResponseEntity.ok(coupons);
    }

    // Get Coupon by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CouponDto>> getCouponById(@PathVariable Long id) {
        return ResponseEntity.ok(couponService.getCouponById(id));
    }

    // Update Coupon
    @PutMapping("/update-coupon/{id}")
    public ResponseEntity<ApiResponse<CouponDto>> updateCoupon(@PathVariable Long id, @RequestBody CouponDto dto) {
        return ResponseEntity.ok(couponService.updateCoupon(id, dto));
    }

    // Delete Coupon
    @DeleteMapping("/delete-coupon/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCoupon(@PathVariable Long id) {
        return ResponseEntity.ok(couponService.deleteCoupon(id));
    }
}
