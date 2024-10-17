package com.sparta.plannerservice.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MergeUserReqDto {

    @NotBlank
    @Size(min = 1, max = 12)
    private String username;

    @NotBlank
    @Size(min = 1, max = 100)
    @Email
    private String email;

    @NotBlank
    @Size(min = 4, max = 100)
    private String password;

}
