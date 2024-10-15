package com.sparta.plannerservice.user.service;

import com.sparta.plannerservice.common.exception.IdNotFoundException;
import com.sparta.plannerservice.user.entity.User;
import com.sparta.plannerservice.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @PersistenceContext
    private final EntityManager em;

    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> readUsers() {
        return userRepository.findAll();
    }

    public User readUser(UUID id) {
        return findUserByIdSafe(id);
    }

    @Transactional
    public void updateUser(UUID id, User user) {
        // dirty checking 사용
        User retrievedUser = findUserByIdSafe(id);
        retrievedUser.setUsername(user.getUsername());
        retrievedUser.setEmail(user.getEmail());
    }

    // deleteById 대신, 사용자 지정 find 메서드와 delete 를 차례대로 수행하여 id 값에 대한 처리를 포함시킵니다.
    @Transactional
    public void deleteUser(UUID id) {
        User retrievedUser = findUserByIdSafe(id);
        userRepository.delete(retrievedUser);
    }

    // 서비스 내부에서만 사용되는 find 용 메서드. id 위치가 존재하지 않을 때 지정된 예외를 발생시킵니다.
    private User findUserByIdSafe(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new IdNotFoundException(User.class, id));
    }
}
