package com.sparta.plannerservice.plan.repository;

import com.sparta.plannerservice.common.exception.IdNotFoundException;
import com.sparta.plannerservice.plan.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlanRepository extends JpaRepository<Plan, UUID> {
    default Plan findByIdSafe(UUID id) {
        return this.findById(id).orElseThrow(() -> new IdNotFoundException(Plan.class, id));
    }
}
