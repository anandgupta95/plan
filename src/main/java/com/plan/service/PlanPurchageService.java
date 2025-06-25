package com.plan.service;

import com.plan.Enum.PlanStatus;
import com.plan.dto.ApiPageResponse;
import com.plan.dto.ApiResponse;
import com.plan.dto.PlanPurchageDto;
import com.plan.entity.Coupon;
import com.plan.entity.Plan;
import com.plan.entity.PlanPurchage;
import com.plan.exception.ResourceNotFoundException;
import com.plan.mapper.PlanPurchageMapper;
import com.plan.repository.CouponRepository;
import com.plan.repository.PlanPurchageRepository;
import com.plan.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

@RequiredArgsConstructor
@Service
public class PlanPurchageService {

    private final PlanPurchageRepository planPurchageRepository;
    private final PlanRepository planRepository;
    private final CouponRepository couponRepository;
    private final PlanPurchageMapper planPurchageMapper;


    // Create PlanPurchage
    public ApiResponse<PlanPurchageDto> createPlanPurchage(PlanPurchageDto dto) {
        Double originalPrice = 0.0;
        Double finalPrice = 0.0;
        Double discountAmount = 0.0;


        if(dto.getCouponId()!=null){
            boolean couponExist = (planPurchageRepository.existByClientIdAndCouponId(dto.getClientId(),dto.getCouponId())>0)?true:false;
            if(couponExist){
                throw new RuntimeException("Coupon already used by this client");
            }
            else{
                Plan plan = planRepository.findById(dto.getPlanId()).orElseThrow(() -> new RuntimeException("Plan does not exist with this id : "+ dto.getPlanId()));
                originalPrice = plan.getPrice();
                Coupon coupon= couponRepository.findById(dto.getPlanId()).orElseThrow(()-> new RuntimeException("Coupon does not exist wiht this is : "+dto.getCouponId()));
                Integer discountPercent = coupon.getDiscountPercent();

                finalPrice = originalPrice - originalPrice*(discountPercent/100);
            }
        }
        else{
            finalPrice = originalPrice;
        }

        PlanPurchage planExist = planPurchageRepository.findByClientIdAndPlanId(dto.getClientId(),dto.getPlanId());
        if(planExist != null){
            if(planExist.getPlanStatus().equals(PlanStatus.RUNNING)){
                throw new RuntimeException("Plan is already Purchage");
            }
        }
        else{
            throw new RuntimeException("Plan is not found with this id : "+ dto.getPlanId());
        }
        PlanPurchage planPurchage = planPurchageMapper.toEntity(dto,originalPrice,finalPrice);
        PlanPurchage savedPlanPurchage = planPurchageRepository.save(planPurchage);
        return new ApiResponse<>(true, "PlanPurchage created successfully!", planPurchageMapper.toDto(savedPlanPurchage));
    }

    public ApiPageResponse<PlanPurchageDto> getAllPlanPurchages(Pageable pageable, Integer week, Integer month, Integer year, LocalDate startDate, LocalDate endDate) {

        // Default: no filtering
        LocalDateTime fromDateTime = null;
        LocalDateTime toDateTime = null;

        LocalDate today = LocalDate.now();

        if(week!=null&&month!=null&&year!=null){
            LocalDate firstDayOfMonth = LocalDate.of(year,month,1);
            LocalDate weekStart = firstDayOfMonth.with(TemporalAdjusters.dayOfWeekInMonth(week, DayOfWeek.MONDAY));
            LocalDate weekEnd = weekStart.plusDays(6);
            fromDateTime = weekStart.atStartOfDay();
            toDateTime = weekEnd.atTime(LocalTime.MAX);
        }
        if(month!=null&&year!=null){
            LocalDate start = LocalDate.of(year,month,1);
            LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
            fromDateTime = start.atStartOfDay();
            toDateTime = end.atTime(LocalTime.MAX);
        }
        if(year!=null){
            LocalDate start = LocalDate.of(year,1,1);
            LocalDate end = LocalDate.of(year,12,31);
            fromDateTime = start.atStartOfDay();
            toDateTime = end.atTime(LocalTime.MAX);

        }
        if(startDate!=null&&endDate!=null){
            fromDateTime = startDate.atStartOfDay();
            toDateTime = endDate.atTime(LocalTime.MAX);
        }

        Page<PlanPurchage> planPurchagePage;

        if (fromDateTime != null && toDateTime != null) {
            planPurchagePage = planPurchageRepository.findByCreatedAtBetween(fromDateTime, toDateTime, pageable);
        } else {
            planPurchagePage = planPurchageRepository.findAll(pageable);
        }

        Page<PlanPurchageDto> dtoPage = planPurchagePage.map(planPurchageMapper::toDto);
        return ApiPageResponse.fromPage(dtoPage, "PlanPurchages fetched successfully");
    }

    // Get PlanPurchage by ID
    public ApiResponse<PlanPurchageDto> getPlanPurchageById(Long id) {
        PlanPurchage planPurchage = planPurchageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PlanPurchage not found with ID: " + id));
        return new ApiResponse<>(true, "PlanPurchage fetched successfully!", planPurchageMapper.toDto(planPurchage));
    }

    // Update PlanPurchage
    public ApiResponse<PlanPurchageDto> updatePlanPurchage(Long planPurchaseId,PlanPurchageDto dto) {
        PlanPurchage planPurchage = planPurchageRepository.findById(planPurchaseId)
                .orElseThrow(() -> new ResourceNotFoundException("PlanPurchase not found with ID: " + planPurchaseId));

//        planPurchage.setCode(dto.getCode());
//        planPurchage.setDiscountDescription(dto.getDiscountDescription());
//        planPurchage.setStatus(dto.getStatus());

        PlanPurchage updatedPlanPurchage = planPurchageRepository.save(planPurchage);
        return new ApiResponse<>(true, "PlanPurchage updated successfully!", planPurchageMapper.toDto(updatedPlanPurchage));
    }

    // Delete PlanPurchage
    public ApiResponse<String> deletePlanPurchage(Long id) {
        PlanPurchage planPurchage = planPurchageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PlanPurchage not found with ID: " + id));
        planPurchageRepository.delete(planPurchage);
        return new ApiResponse<>(true,"purchage plan deleted successfully!",null);
    }
}
