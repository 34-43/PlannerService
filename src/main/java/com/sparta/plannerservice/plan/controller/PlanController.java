package com.sparta.plannerservice.plan.controller;

import com.sparta.plannerservice.plan.dto.MergePlanReqDto;
import com.sparta.plannerservice.plan.dto.ReadPlanResDto;
import com.sparta.plannerservice.plan.entity.Plan;
import com.sparta.plannerservice.plan.service.PlanService;
import com.sparta.plannerservice.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/plan")
@RequiredArgsConstructor
public class PlanController {
    private final PlanService planService;

    @PostMapping
    public ReadPlanResDto createPlan(HttpServletRequest HttpReq, @RequestBody@Valid final MergePlanReqDto req) {
        User jwtUser = getUserAttribute(HttpReq);
        Plan reqPlan = req.toEntity();
        Plan retrievedPlan = planService.createPlan(jwtUser, reqPlan);
        return new ReadPlanResDto(retrievedPlan);
    }

    @GetMapping
    public List<ReadPlanResDto> readPlans(HttpServletRequest HttpReq, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        User jwtUser = getUserAttribute(HttpReq);
        return planService.readPlans(jwtUser, page, size).stream().map(ReadPlanResDto::new).toList();
    }

    @GetMapping("/id/{id}")
    public ReadPlanResDto readPlan(HttpServletRequest HttpReq, @PathVariable UUID id) {
        User jwtUser = getUserAttribute(HttpReq);
        Plan retrievedPlan = planService.readPlan(jwtUser, id);
        return new ReadPlanResDto(retrievedPlan);
    }

    @PutMapping("/id/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePlan(HttpServletRequest HttpReq, @PathVariable UUID id, @RequestBody @Valid final MergePlanReqDto req) {
        User jwtUser = getUserAttribute(HttpReq);
        Plan reqPlan = req.toEntity();
        planService.updatePlan(jwtUser, id, reqPlan);
    }

    @PutMapping("/id/{id}/join/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void joinPlan(HttpServletRequest HttpReq, @PathVariable UUID id, @PathVariable UUID userId) {
        User jwtUser = getUserAttribute(HttpReq);
        planService.joinUserInPlan(jwtUser, id, userId);
    }

    @DeleteMapping("/id/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlan(HttpServletRequest HttpReq, @PathVariable UUID id) {
        User jwtUser = getUserAttribute(HttpReq);
        planService.deletePlan(jwtUser, id);
    }

    private User getUserAttribute(HttpServletRequest httpReq) {
        return (User) httpReq.getAttribute("user");
    }
}
