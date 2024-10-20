package com.sparta.plannerservice.plan.dto;

import com.sparta.plannerservice.common.dto.MergeReqDto;
import com.sparta.plannerservice.plan.entity.Plan;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MergePlanReqDto implements MergeReqDto<Plan> {

    @NotBlank
    @Size(min = 1, max = 50)
    private String title;

    @NotNull
    private String content;

    @Override
    public Plan toEntity() {
        return Plan.builder()
                .title(title)
                .content(content)
                .build();
    }
}
