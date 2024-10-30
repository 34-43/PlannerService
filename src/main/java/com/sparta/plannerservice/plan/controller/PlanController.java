package com.sparta.plannerservice.plan.controller;

import com.sparta.plannerservice.plan.dto.MergePlanReqDto;
import com.sparta.plannerservice.plan.dto.ReadPlanResDto;
import com.sparta.plannerservice.plan.entity.Plan;
import com.sparta.plannerservice.plan.service.PlanService;
import com.sparta.plannerservice.plan.service.WeatherApiService;
import com.sparta.plannerservice.plan.util.PlanFactory;
import com.sparta.plannerservice.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class PlanController {
    private final PlanService planService;
    private final WeatherApiService weatherApiService;
    private final PlanFactory planFactory;

    @PostMapping
    public ResponseEntity<ReadPlanResDto> createPlan(@RequestAttribute("user") User jwtUser, @RequestBody @Valid final MergePlanReqDto req) {
        String weather = weatherApiService.fetchWeather(LocalDate.now());
        Plan reqPlan = planFactory.createPlan(req, weather);
        Plan retrievedPlan = planService.createPlan(jwtUser, reqPlan);
        URI path = URI.create("/api/plans/id/" + retrievedPlan.getId());
        return ResponseEntity.created(path).body(new ReadPlanResDto(retrievedPlan));
    }

    @GetMapping
    public ResponseEntity<List<ReadPlanResDto>> readPlans(
            @RequestAttribute("user") User jwtUser,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "updatedAt") String sortBy,
            @RequestParam(defaultValue = "desc") String order
    ) {
        return ResponseEntity.ok(planService.readPlans(jwtUser, page, size, sortBy, order).stream().map(ReadPlanResDto::new).toList());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ReadPlanResDto> readPlan(@RequestAttribute("user") User jwtUser, @PathVariable UUID id) {
        Plan retrievedPlan = planService.readPlan(jwtUser, id);
        return ResponseEntity.ok(new ReadPlanResDto(retrievedPlan));
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<Void> updatePlan(@RequestAttribute("user") User jwtUser, @PathVariable UUID id, @RequestBody @Valid final MergePlanReqDto req) {
        String weather = weatherApiService.fetchWeather(LocalDate.now());
        Plan reqPlan = planFactory.createPlan(req, weather);
        planService.updatePlan(jwtUser, id, reqPlan);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deletePlan(@RequestAttribute("user") User jwtUser, @PathVariable UUID id) {
        planService.deletePlan(jwtUser, id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/id/{id}/users/{userId}")
    public ResponseEntity<Void> joinPlan(@RequestAttribute("user") User jwtUser, @PathVariable UUID id, @PathVariable UUID userId) {
        planService.joinPlan(jwtUser, id, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/id/{id}/users/self")
    public ResponseEntity<Void> leavePlan(@RequestAttribute("user") User jwtUser, @PathVariable UUID id) {
        planService.leavePlan(jwtUser, id);
        return ResponseEntity.noContent().build();
    }

}
