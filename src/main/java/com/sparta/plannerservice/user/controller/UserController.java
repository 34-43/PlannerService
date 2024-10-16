package com.sparta.plannerservice.user.controller;

import com.sparta.plannerservice.user.dto.MergeUserReqDto;
import com.sparta.plannerservice.user.dto.ReadUserResDto;
import com.sparta.plannerservice.user.entity.User;
import com.sparta.plannerservice.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ReadUserResDto createUser(@RequestBody @Valid final MergeUserReqDto req) {
        User reqUser = req.toEntity();
        User retrievedUser = userService.createUser(reqUser);
        return new ReadUserResDto(retrievedUser);
    }

    @GetMapping
    public List<ReadUserResDto> readUsers() {
        return userService.readUsers().stream().map(ReadUserResDto::new).toList();
    }

    @GetMapping("/{id}")
    public ReadUserResDto readUser(@PathVariable UUID id) {
        User retrievedUser = userService.readUser(id);
        return new ReadUserResDto(retrievedUser);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable UUID id, @RequestBody @Valid final MergeUserReqDto req) {
        User reqUser = req.toEntity();
        userService.updateUser(id, reqUser);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }

}
