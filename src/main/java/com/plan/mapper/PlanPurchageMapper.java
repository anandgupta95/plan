package com.plan.mapper;

import com.plan.dto.PlanPurchageDto;
import com.plan.entity.PlanPurchage;
import org.springframework.stereotype.Component;

@Component
public class PlanPurchageMapper {

    public PlanPurchage toEntity(PlanPurchageDto dto,Double originalPrice, Double finalPrice) {
        PlanPurchage planPurchage = new PlanPurchage();
        planPurchage.setClientId(dto.getClientId());
        planPurchage.setCouponId(dto.getCouponId());
        planPurchage.setTransactionId(dto.getTransactionId());
        planPurchage.setPlanId(dto.getPlanId());
        planPurchage.setOriginalPrice(dto.getOriginalPrice());
        planPurchage.setFinalPrice(dto.getFinalPrice());

        return planPurchage;
    }

    public PlanPurchageDto toDto(PlanPurchage planPurchage) {
        PlanPurchageDto dto = new PlanPurchageDto();
        dto.setId(planPurchage.getId());
        dto.setClientId(planPurchage.getClientId());
        dto.setCouponId(planPurchage.getCouponId());
        dto.setTransactionId(planPurchage.getTransactionId());
        dto.setCreatedAt(planPurchage.getCreatedAt());
        dto.setUpdatedAt(planPurchage.getUpdatedAt());
        dto.setFinalPrice(planPurchage.getFinalPrice());
        dto.setDiscountAmount(planPurchage.getDiscountAmount());
        dto.setOriginalPrice(planPurchage.getOriginalPrice());
        dto.setPaymentStatus(planPurchage.getPaymentStatus());
        dto.setPlanStatus(planPurchage.getPlanStatus());
        return dto;
    }
}




