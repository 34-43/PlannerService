package com.sparta.plannerservice.user.entity;

import com.sparta.plannerservice.common.entity.UuidEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends UuidEntity {

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;



}
