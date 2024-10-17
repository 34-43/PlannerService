package com.sparta.plannerservice.login.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginReqDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 4, max = 100)
    private String password;
}
