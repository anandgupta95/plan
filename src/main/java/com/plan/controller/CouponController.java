package com.plan.controller;

import com.plan.dto.ApiPageResponse;
import com.plan.dto.ApiResponse;
import com.plan.dto.CouponDto;
import com.plan.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/coupons")
@Tag(name = "coupon management", description = "operation related to coupon")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    // Create Coupon
    @Operation(summary = "Create a new coupon")
    @PostMapping("/create-coupon")
    public ResponseEntity<ApiResponse<CouponDto>> createCoupon(@RequestBody CouponDto dto) {
        return ResponseEntity.ok(couponService.createCoupon(dto));
    }

    // Get All Coupons (Pagination, Sorting)
    @Operation(summary = "fetch all coupon using pagination ")
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
    @Operation(summary = "fetch one coupon")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CouponDto>> getCouponById(@PathVariable Long id) {
        return ResponseEntity.ok(couponService.getCouponById(id));
    }

    // Update Coupon
    @Operation(summary = "update coupon")
    @PutMapping("/update-coupon/{id}")
    public ResponseEntity<ApiResponse<CouponDto>> updateCoupon(@PathVariable Long id, @RequestBody CouponDto dto) {
        return ResponseEntity.ok(couponService.updateCoupon(id, dto));
    }

    // Delete Coupon
    @Operation(summary = "delete coupon")
    @DeleteMapping("/delete-coupon/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCoupon(@PathVariable Long id) {
        return ResponseEntity.ok(couponService.deleteCoupon(id));
    }
}
