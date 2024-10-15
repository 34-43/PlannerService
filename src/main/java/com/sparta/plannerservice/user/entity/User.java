package com.sparta.plannerservice.user.entity;

import com.sparta.plannerservice.common.entity.UuidEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends UuidEntity {

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

}
