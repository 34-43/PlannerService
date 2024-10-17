package com.sparta.plannerservice.login.controller;

import com.sparta.plannerservice.login.service.LoginService;
import com.sparta.plannerservice.common.util.JwtUtil;
import com.sparta.plannerservice.login.dto.LoginReqDto;
import com.sparta.plannerservice.user.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/login")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    private final JwtUtil jwtUtil;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void login(@RequestBody @Valid LoginReqDto req, HttpServletResponse res) {
        User authUser = loginService.authenticate(req.getEmail(), req.getPassword());
        String token = jwtUtil.createToken(authUser.getId().toString());
        jwtUtil.addJwtToCookie(token, res);
    }
}
