package com.sparta.plannerservice.user.repository;

import com.sparta.plannerservice.common.exception.IdNotFoundException;
import com.sparta.plannerservice.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    default User findByIdSafe(UUID id) {
        return this.findById(id).orElseThrow(() -> new IdNotFoundException(User.class, id));
    }
}
