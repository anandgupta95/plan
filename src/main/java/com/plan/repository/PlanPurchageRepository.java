package com.plan.repository;

import com.plan.entity.PlanPurchage;
import jakarta.transaction.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PlanPurchageRepository extends JpaRepository<PlanPurchage,Long> {
    Page<PlanPurchage> findByCreatedAtBetween(LocalDateTime fromDateTime, LocalDateTime toDateTime, Pageable pageable);

    Optional<PlanPurchage> findByTransactionId(Long transactionId);
    @Query(value = "select Count(*)>0 from planpurchases where client_id = :clientId and coupon_id = :couponId", nativeQuery = true)
    Long existByClientIdAndCouponId(@Param("clientId") Long clientId, @Param("couponId") Long couponId);

    @Query(value = "select * from planpurchases where client_id = :clientId and plan_id = :planId", nativeQuery = true)
    PlanPurchage findByClientIdAndPlanId(@Param("clientId") Long clientId, @Param("planId") Long planId);


}
