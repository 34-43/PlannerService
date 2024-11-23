package com.sparta.plannerservice.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PlannerRole {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String role;
}
