package com.plan.service;

import com.plan.dto.ApiPageResponse;
import com.plan.dto.ApiResponse;
import com.plan.dto.PlanDto;
import com.plan.entity.Plan;
import com.plan.exception.ResourceNotFoundException;
import com.plan.mapper.PlanMapper;
import com.plan.repository.PlanRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

//import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Service
public class PlanService {

    private final PlanRepository planRepository;
    private final PlanMapper planMapper;

    public PlanService(PlanRepository planRepository, PlanMapper planMapper) {
        this.planRepository = planRepository;
        this.planMapper = planMapper;
    }

    public ApiResponse<PlanDto> createPlan(PlanDto dto) {
        if(planRepository.findByPlanName(dto.getPlanName()).size() > 0 ){
            throw new RuntimeException("plan already exist with this name : "+ dto.getPlanName());
        }
        Plan plan = planMapper.toEntity(dto);
        return new ApiResponse<>(true,"plan created successfully!",planMapper.toDto(planRepository.save(plan)));
    }

public ApiPageResponse<PlanDto> getAllPlans(Pageable pageable, Integer week, Integer month, Integer year, LocalDate startDate, LocalDate endDate) {

    // Default: no filtering
    LocalDateTime fromDateTime = null;
    LocalDateTime toDateTime = null;

    LocalDate today = LocalDate.now();


    Page<Plan> planPage;

    if (fromDateTime != null && toDateTime != null) {
        planPage = planRepository.findByCreatedAtBetween(fromDateTime, toDateTime, pageable);
    } else {
        planPage = planRepository.findAll(pageable);
    }

    Page<PlanDto> dtoPage = planPage.map(planMapper::toDto);
    return ApiPageResponse.fromPage(dtoPage, "Plans fetched successfully");
}



    public ApiResponse<PlanDto> getPlanById(Long id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found with ID: " + id));
        return new ApiResponse<>(true,"plan fetched successfully!",planMapper.toDto(plan));
    }

    public ApiResponse<PlanDto> updatePlan(Long id, PlanDto dto) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found with ID: " + id));
        if(!plan.getPlanName().equals(dto.getPlanName())){
            if(planRepository.findByPlanName(dto.getPlanName()).size() > 0){
                throw new RuntimeException("PlanName is already exist");
            }
        }

        plan.setPlanName(dto.getPlanName());
        plan.setDescription(dto.getDescription());
        plan.setPrice(dto.getPrice());
        return new ApiResponse<>(true,"plan updated successfully!",planMapper.toDto(planRepository.save(plan)));
    }

    public ApiResponse<String> deletePlan(Long id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found with ID: " + id));
        planRepository.delete(plan);
        return new ApiResponse<>(true,"plan deleted successfully!",null);
    }
}