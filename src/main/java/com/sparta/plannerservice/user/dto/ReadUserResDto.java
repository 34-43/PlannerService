package com.sparta.plannerservice.user.dto;

import com.sparta.plannerservice.common.dto.UuidEntityResDto;
import com.sparta.plannerservice.common.enums.PlannerRole;
import com.sparta.plannerservice.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReadUserResDto extends UuidEntityResDto {
    private PlannerRole role;
    private String username;
    private String email;

    public ReadUserResDto(User user) {
        super(user);
        this.role = user.getRole();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
