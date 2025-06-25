package com.plan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.plan.entity.Coupon;

import java.time.LocalDateTime;
public interface CouponRepository extends JpaRepository<Coupon,Long> {
    Page<Coupon> findByCreatedAtBetween(LocalDateTime fromDateTime, LocalDateTime toDateTime, Pageable pageable);
}
