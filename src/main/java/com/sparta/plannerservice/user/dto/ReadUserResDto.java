package com.sparta.plannerservice.user.dto;

import com.sparta.plannerservice.common.dto.LongIdKeyEntityResDto;
import com.sparta.plannerservice.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReadUserResDto extends LongIdKeyEntityResDto {
    private String username;
    private String email;

    public ReadUserResDto(User user) {
        // 부모 Dto 클래스에서 정의된 필드(id 등)의 대입은 super 로 수행
        super(user);
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
