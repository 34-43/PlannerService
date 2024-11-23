package com.sparta.plannerservice.plan.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WeatherApiFetchDto {
    private String date;
    private String weather;
}
