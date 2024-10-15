package com.sparta.plannerservice.common.dto;

import com.sparta.plannerservice.common.entity.TimestampedEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TimestampedEntityResDto {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TimestampedEntityResDto(TimestampedEntity entity) {
        this.createdAt =  entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }
}
