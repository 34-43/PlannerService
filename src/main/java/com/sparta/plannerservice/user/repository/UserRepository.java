package com.sparta.plannerservice.user.repository;

import com.sparta.plannerservice.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
