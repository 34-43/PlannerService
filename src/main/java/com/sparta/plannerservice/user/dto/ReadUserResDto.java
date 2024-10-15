package com.sparta.plannerservice.user.dto;

import com.sparta.plannerservice.common.dto.UuidEntityResDto;
import com.sparta.plannerservice.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReadUserResDto extends UuidEntityResDto {
    private String username;
    private String email;

    public ReadUserResDto(User user) {
        super(user);
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
