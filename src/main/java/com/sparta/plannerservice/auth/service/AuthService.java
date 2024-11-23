package com.sparta.plannerservice.auth.service;

import com.sparta.plannerservice.common.enums.FailedRequest;
import com.sparta.plannerservice.common.exception.FailedRequestException;
import com.sparta.plannerservice.common.util.PasswordUtil;
import com.sparta.plannerservice.user.entity.User;
import com.sparta.plannerservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordUtil passwordUtil;

    public User authenticate(String email, String password) {
        User retrievedUser = userRepository.findByEmail(email).orElseThrow(() -> new FailedRequestException(FailedRequest.EMAIL_NOT_FOUND));

        if (!passwordUtil.matches(password, retrievedUser.getPasswordHash())) {
            throw new FailedRequestException(FailedRequest.PASSWORD_INCORRECT);
        }

        return retrievedUser;
    }
}
