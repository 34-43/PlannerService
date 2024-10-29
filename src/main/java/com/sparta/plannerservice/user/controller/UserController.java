package com.sparta.plannerservice.user.controller;

import com.sparta.plannerservice.user.dto.MergeUserReqDto;
import com.sparta.plannerservice.user.dto.ReadUserResDto;
import com.sparta.plannerservice.user.entity.User;
import com.sparta.plannerservice.user.service.UserService;
import com.sparta.plannerservice.user.util.UserFactory;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserFactory userFactory;

    @PostMapping
    public ResponseEntity<ReadUserResDto> registerUser(@RequestBody @Valid final MergeUserReqDto req) {
        User reqUser = userFactory.createUser(req);
        User retrievedUser = userService.registerUser(reqUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ReadUserResDto(retrievedUser));
    }

    @GetMapping
    public ResponseEntity<List<ReadUserResDto>> readUsers() {
        List<User> users = userService.readUsers();
        return ResponseEntity.ok(users.stream().map(ReadUserResDto::new).toList());
    }

    @GetMapping("/self")
    public ResponseEntity<ReadUserResDto> readSelf(@RequestAttribute("user") User jwtUser) {
        return ResponseEntity.ok(new ReadUserResDto(jwtUser));
    }

    @PutMapping("/self")
    public ResponseEntity<Void> updateSelf(@RequestAttribute("user") User jwtUser, @RequestBody @Valid final MergeUserReqDto req) {
        User reqUser = userFactory.createUser(req);
        userService.updateUser(jwtUser, reqUser);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/self")
    public ResponseEntity<Void> deleteSelf(@RequestAttribute("user") User jwtUser, HttpServletResponse httpRes) {
        userService.deleteUser(httpRes, jwtUser);
        return ResponseEntity.noContent().build();
    }

}
