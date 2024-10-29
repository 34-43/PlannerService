package com.sparta.plannerservice.user.util;

import com.sparta.plannerservice.common.enums.PlannerRole;
import com.sparta.plannerservice.common.util.PasswordUtil;
import com.sparta.plannerservice.user.dto.MergeUserReqDto;
import com.sparta.plannerservice.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFactory {
    private final PasswordUtil passwordUtil;

    public User createUser(MergeUserReqDto req) {
        return User.builder()
                .role(PlannerRole.USER)
                .username(req.getUsername())
                .email(req.getEmail())
                .passwordHash(passwordUtil.encode(req.getPassword()))
                .build();
    }

}
