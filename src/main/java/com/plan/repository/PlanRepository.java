package com.plan.repository;

import com.plan.entity.Plan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PlanRepository extends JpaRepository<Plan,Long> {
    Page<Plan> findByCreatedAtBetween(LocalDateTime fromDateTime, LocalDateTime toDateTime, Pageable pageable);
    @Query(value = "select * from plans where plan_name = :planName", nativeQuery = true)
    List<Plan> findByPlanName(@Param("planName") String planName);
}
