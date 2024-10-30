package com.sparta.plannerservice.user.dto;

import com.sparta.plannerservice.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReadPublicUserResDto {
    private String username;
    private String email;

    public ReadPublicUserResDto(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
