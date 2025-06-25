package com.plan.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import com.plan.Enum.*;


@Getter
@Setter
@Entity
@Table(name = "planpurchases")
public class PlanPurchage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long planId;
    private Long couponId;
    private Long clientId;
    @Column(unique = true)
    private Long transactionId;

    private Double discountAmount;
    private Double originalPrice;
    private Double finalPrice;

    private PaymentStatus paymentStatus = PaymentStatus.PENDING;
    private PlanStatus planStatus = PlanStatus.PENDING;



    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}