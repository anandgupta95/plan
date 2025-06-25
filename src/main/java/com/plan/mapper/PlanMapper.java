package com.plan.mapper;

import com.plan.dto.PlanDto;
import com.plan.entity.Plan;
import org.springframework.stereotype.Component;

@Component
public class PlanMapper {

    public Plan toEntity(PlanDto dto) {
        Plan plan = new Plan();
        plan.setPlanName(dto.getPlanName());
        plan.setDescription(dto.getDescription());
        plan.setPrice(dto.getPrice());
        return plan;
    }

    public PlanDto toDto(Plan plan) {
        PlanDto dto = new PlanDto();
        dto.setId(plan.getId());
        dto.setPlanName(plan.getPlanName());
        dto.setDescription(plan.getDescription());
        dto.setPrice(plan.getPrice());
        dto.setCreatedAt(plan.getCreatedAt());
        dto.setUpdatedAt(plan.getUpdatedAt());
        return dto;
    }
}




