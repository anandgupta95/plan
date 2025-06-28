package com.plan.controller;

import com.plan.dto.*;
import com.plan.service.PlanPurchageService;
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
@RequestMapping("/api/planpurchages")
@Tag(name = "planpurchage management", description = "all the operation related to planpurchage")
public class PlanPurchageController {

    private final PlanPurchageService planPurchageService;

    public PlanPurchageController(PlanPurchageService planPurchageService) {
        this.planPurchageService = planPurchageService;
    }

    @Operation(summary = "create plan purchage")
    @PostMapping("/create-planPurchage")
    public ResponseEntity<ApiResponse<PlanPurchageDto>> createPlanPurchage(@RequestBody PlanPurchageDto dto) {
        return ResponseEntity.ok(planPurchageService.createPlanPurchage(dto));
    }

    @Operation(summary = "fetch all planpurchage using pagination and filter week, month, year ")
    @GetMapping("/all")
    public ResponseEntity<ApiPageResponse<PlanPurchageDto>> getAllPlanPurchages(
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
        ApiPageResponse<PlanPurchageDto> planPurchages = planPurchageService.getAllPlanPurchages(pageable,week,month, year, startDate, endDate);
        return ResponseEntity.ok(planPurchages);
    }

    @Operation(summary = "fetch one operation")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PlanPurchageDto>> getPlanPurchageById(@PathVariable Long id) {
        return ResponseEntity.ok(planPurchageService.getPlanPurchageById(id));
    }

    @Operation(summary = "update planpurchage")
    @PutMapping("/update-planPurchage/{id}")
    public ResponseEntity<ApiResponse<PlanPurchageDto>> updatePlanPurchage(@PathVariable Long id, @RequestBody PlanPurchageDto dto) {
        return ResponseEntity.ok(planPurchageService.updatePlanPurchage(id, dto));
    }

    @Operation(summary = "delete planpurchage")
    @DeleteMapping("/delete-planPurchage/{id}")
    public ResponseEntity<ApiResponse<String>> deletePlanPurchage(@PathVariable Long id) {
        return ResponseEntity.ok(planPurchageService.deletePlanPurchage(id));
    }
}