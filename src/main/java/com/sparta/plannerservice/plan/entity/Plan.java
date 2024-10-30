package com.sparta.plannerservice.plan.entity;

import com.sparta.plannerservice.common.entity.UuidEntity;
import com.sparta.plannerservice.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plan extends UuidEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToMany(mappedBy = "plans", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();
    
}
