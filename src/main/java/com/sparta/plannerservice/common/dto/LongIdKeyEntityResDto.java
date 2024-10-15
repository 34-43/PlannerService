package com.sparta.plannerservice.common.dto;

import com.sparta.plannerservice.common.entity.LongIdKeyEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

// TimestampedEntity -> 응답 Dto 를 위한 생성자에서 반복되는 필드들의 대입을 분리시킨 클래스

@Getter
@NoArgsConstructor
public class LongIdKeyEntityResDto extends TimestampedEntityResDto {
    private Long id;

    public LongIdKeyEntityResDto(LongIdKeyEntity entity) {
        super(entity);
        this.id = entity.getId();
    }
}
