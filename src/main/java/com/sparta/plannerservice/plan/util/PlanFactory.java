package com.sparta.plannerservice.plan.util;

import com.sparta.plannerservice.plan.dto.MergePlanReqDto;
import com.sparta.plannerservice.plan.entity.Plan;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PlanFactory {
    public Plan createPlan(MergePlanReqDto req, String weather) {
        return Plan.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .weather(weather)
                .users(new ArrayList<>())
                .build();
    }
}
