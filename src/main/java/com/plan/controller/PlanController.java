package com.plan.controller;

import com.plan.dto.*;
import com.plan.service.PlanService;
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
@RequestMapping("/api/plans")
@Tag(name = "plan management", description = "all the plan operations")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @Operation(summary = "create plan")
    @PostMapping("/create-plan")
    public ResponseEntity<ApiResponse<PlanDto>> createPlan(@RequestBody PlanDto dto) {
        return ResponseEntity.ok(planService.createPlan(dto));
    }

    @Operation(summary = "fetch all plans")
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

    @Operation(summary = "fetch single plans")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PlanDto>> getPlanById(@PathVariable Long id) {
        return ResponseEntity.ok(planService.getPlanById(id));
    }

    @Operation(summary = "update plans")
    @PutMapping("/update-plan/{id}")
    public ResponseEntity<ApiResponse<PlanDto>> updatePlan(@PathVariable Long id, @RequestBody PlanDto dto) {
        return ResponseEntity.ok(planService.updatePlan(id, dto));
    }

    @Operation(summary = "delete plans")
    @DeleteMapping("/delete-plan/{id}")
    public ResponseEntity<ApiResponse<String>> deletePlan(@PathVariable Long id) {
        return ResponseEntity.ok(planService.deletePlan(id));
    }
}