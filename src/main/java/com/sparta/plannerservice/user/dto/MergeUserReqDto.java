package com.sparta.plannerservice.user.dto;

import com.sparta.plannerservice.common.dto.MergeReqDto;
import com.sparta.plannerservice.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MergeUserReqDto implements MergeReqDto<User> {

    @NotBlank
    @Size(min = 1, max = 12)
    private String username;

    @NotBlank
    @Size(min = 1, max = 100)
    @Email
    private String email;

    @Override
    public User toEntity() {
        return User.builder()
                .username(username)
                .email(email)
                .build();
    }
}
