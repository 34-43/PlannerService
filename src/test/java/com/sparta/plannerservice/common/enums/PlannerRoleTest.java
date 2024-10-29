package com.sparta.plannerservice.common.enums;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PlannerRoleTest {
    @Test
    @DisplayName("role type test")
    void test() {
        System.out.println(PlannerRole.USER);
        Assertions.assertEquals("ROLE_USER", PlannerRole.USER.getRole());
    }
}