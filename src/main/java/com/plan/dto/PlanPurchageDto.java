package com.plan.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.plan.Enum.PaymentStatus;
import com.plan.Enum.PlanStatus;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanPurchageDto {
    private Long id;

    private Long clientId;
    private Long couponId;
    private Long transactionId;
    private Long planId;

    private Double discountAmount;
    private Double originalPrice;
    private Double finalPrice;

    private PaymentStatus paymentStatus ;
    private PlanStatus planStatus ;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
