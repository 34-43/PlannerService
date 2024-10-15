package com.sparta.plannerservice.common.dto;

import com.sparta.plannerservice.common.entity.UuidEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class UuidEntityResDto extends TimestampedEntityResDto {
    private UUID id;

    public UuidEntityResDto(UuidEntity entity) {
        super(entity);
        this.id = entity.getId();
    }
}
