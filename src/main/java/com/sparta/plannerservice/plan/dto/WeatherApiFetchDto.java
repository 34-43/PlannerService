package com.sparta.plannerservice.plan.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WeatherApiFetchDto {
    private String date;
    private String weather;
}
