package com.sparta.plannerservice.plan.repository;

import com.sparta.plannerservice.common.enums.FailedRequest;
import com.sparta.plannerservice.common.exception.FailedRequestException;
import com.sparta.plannerservice.plan.entity.Plan;
import com.sparta.plannerservice.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface PlanRepository extends JpaRepository<Plan, UUID> {
    default Plan findByIdSafe(UUID id) {
        return this.findById(id).orElseThrow(() -> new FailedRequestException(FailedRequest.ID_NOT_FOUND));
    }

    @Query("SELECT p FROM Plan p JOIN p.users u WHERE u = :user")
    Page<Plan> findAllByUserId(@Param("user") User user, Pageable pageable);
}
