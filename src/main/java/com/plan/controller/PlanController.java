package com.plan.controller;

import com.plan.dto.*;
import com.plan.service.PlanService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/plans")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @PostMapping("/create-plan")
    public ResponseEntity<ApiResponse<PlanDto>> createPlan(@RequestBody PlanDto dto) {
        return ResponseEntity.ok(planService.createPlan(dto));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiPageResponse<PlanDto>> getAllPlans(
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
        ApiPageResponse<PlanDto> plans = planService.getAllPlans(pageable, week, month, year, startDate, endDate);
        return ResponseEntity.ok(plans);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PlanDto>> getPlanById(@PathVariable Long id) {
        return ResponseEntity.ok(planService.getPlanById(id));
    }

    @PutMapping("/update-plan/{id}")
    public ResponseEntity<ApiResponse<PlanDto>> updatePlan(@PathVariable Long id, @RequestBody PlanDto dto) {
        return ResponseEntity.ok(planService.updatePlan(id, dto));
    }

    @DeleteMapping("/delete-plan/{id}")
    public ResponseEntity<ApiResponse<String>> deletePlan(@PathVariable Long id) {
        return ResponseEntity.ok(planService.deletePlan(id));
    }
}