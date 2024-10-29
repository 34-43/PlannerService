package com.sparta.plannerservice.user.service;

import com.sparta.plannerservice.common.util.JwtUtil;
import com.sparta.plannerservice.user.entity.User;
import com.sparta.plannerservice.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public User readUser(UUID id) {
        return userRepository.findByIdSafe(id);
    }

    @Transactional
    public void updateUser(UUID id, User user) {
        User retrievedUser = userRepository.findByIdSafe(id);
        // dirty checking 사용
        retrievedUser.setUsername(user.getUsername());
        retrievedUser.setEmail(user.getEmail());
        retrievedUser.setPasswordHash(user.getPasswordHash());
    }

    // deleteById 대신, 사용자 지정 find 메서드와 delete 를 차례대로 수행하여 id 값에 대한 처리를 포함시킵니다.
    @Transactional
    public void deleteUser(UUID id) {
        User retrievedUser = userRepository.findByIdSafe(id);
        userRepository.delete(retrievedUser);
    }
}
