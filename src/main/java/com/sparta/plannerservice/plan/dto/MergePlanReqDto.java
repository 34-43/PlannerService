package com.sparta.plannerservice.plan.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MergePlanReqDto {

    @NotBlank
    @Size(min = 1, max = 50)
    private String title;

    @NotNull
    private String content;
}
