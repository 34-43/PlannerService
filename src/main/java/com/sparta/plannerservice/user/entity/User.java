package com.sparta.plannerservice.user.entity;

import com.sparta.plannerservice.common.entity.UuidEntity;
import com.sparta.plannerservice.common.enums.PlannerRole;
import com.sparta.plannerservice.plan.entity.Plan;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends UuidEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PlannerRole role;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_plan",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "plan_id")
    )
    private Set<Plan> plans = new HashSet<>();

}
