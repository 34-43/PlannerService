package com.sparta.plannerservice.user.service;

import com.sparta.plannerservice.common.util.JwtUtil;
import com.sparta.plannerservice.user.entity.User;
import com.sparta.plannerservice.user.exception.EmailDuplicantException;
import com.sparta.plannerservice.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public User registerUser(User user) {
        // 이메일 중복 확인 절차
        String reqEmail = user.getEmail();
        if (userRepository.existsByEmail(reqEmail)) {
            throw new EmailDuplicantException(reqEmail);
        }
        return userRepository.save(user);
    }

    public List<User> readUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void updateUser(User jwtUser, User reqUser) {
        // jwt 파싱 과정에서 얻은 user 는 detached 이기에 dirty checking 불가능
        // jwt 필터가 바로 직전에 user 의 존재를 보장해줌.
        // 간접적으로 merge 를 호출해 주어야 함.
        jwtUser.setUsername(reqUser.getUsername());
        jwtUser.setEmail(reqUser.getEmail());
        jwtUser.setPasswordHash(reqUser.getPasswordHash());
        userRepository.save(jwtUser);
    }

    @Transactional
    public void deleteUser(HttpServletResponse httpRes, User jwtUser) {
        userRepository.delete(jwtUser);
        removeCookie(httpRes);
    }

    // 현재 토큰 사용자가 삭제될 때, 클라이언트의 쿠키를 덮어씌워 제거하기 위한 메서드
    private void removeCookie(HttpServletResponse httpRes) {
        jwtUtil.addJwtToCookie(null, httpRes);
    }

}
