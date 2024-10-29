package com.sparta.plannerservice.auth.controller;

import com.sparta.plannerservice.common.util.JwtUtil;
import com.sparta.plannerservice.auth.dto.LoginReqDto;
import com.sparta.plannerservice.auth.service.AuthService;
import com.sparta.plannerservice.user.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Valid LoginReqDto req, HttpServletResponse res) {
        User authUser = authService.authenticate(req.getEmail(), req.getPassword());
        String token = jwtUtil.createToken(authUser.getId(), authUser.getRole());
        jwtUtil.addJwtToCookie(token, res);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestAttribute("user") User jwtUser, HttpServletResponse res) {
        if (Objects.isNull(jwtUser)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        jwtUtil.addJwtToCookie(null, res);
        return ResponseEntity.noContent().build();
    }
}
