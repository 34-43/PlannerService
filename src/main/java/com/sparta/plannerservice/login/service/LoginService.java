package com.sparta.plannerservice.login.service;

import com.sparta.plannerservice.common.exception.EmailUserNotFoundException;
import com.sparta.plannerservice.common.exception.PasswordFailException;
import com.sparta.plannerservice.common.util.PasswordUtil;
import com.sparta.plannerservice.user.entity.User;
import com.sparta.plannerservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;
    private final PasswordUtil passwordUtil;

    public User authenticate(String email, String password) {
        User retrievedUser = userRepository.findByEmail(email).orElseThrow(EmailUserNotFoundException::new);

        if (!passwordUtil.matches(password, retrievedUser.getPasswordHash())) {
            throw new PasswordFailException();
        }

        return retrievedUser;
    }
}
