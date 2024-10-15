package com.sparta.plannerservice.common.dto;

import com.sparta.plannerservice.common.entity.UuidEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class UuidEntityResDto extends TimestampedEntityResDto {
    private UUID uuid;

    public UuidEntityResDto(UuidEntity entity) {
        super(entity);
        this.uuid = entity.getUuid();
    }
}
