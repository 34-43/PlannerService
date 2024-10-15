package com.sparta.plannerservice.user.repository;

import com.sparta.plannerservice.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {}
