package com.sparta.plannerservice.plan.dto;

import com.sparta.plannerservice.common.dto.UuidEntityResDto;
import com.sparta.plannerservice.plan.entity.Plan;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReadPlanResDto extends UuidEntityResDto {
    private String title;
    private String content;
    private String weather;

    public ReadPlanResDto(Plan plan) {
        super(plan);
        this.title = plan.getTitle();
        this.content = plan.getContent();
        this.weather = plan.getWeather();
    }
}
