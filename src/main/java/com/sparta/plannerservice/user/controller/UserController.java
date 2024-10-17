package com.sparta.plannerservice.user.controller;

import com.sparta.plannerservice.common.util.PasswordUtil;
import com.sparta.plannerservice.user.dto.MergeUserReqDto;
import com.sparta.plannerservice.user.dto.ReadUserResDto;
import com.sparta.plannerservice.user.entity.User;
import com.sparta.plannerservice.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    private final PasswordUtil passwordUtil;

    @PostMapping
    public ReadUserResDto registerUser(@RequestBody @Valid final MergeUserReqDto req) {
        User reqUser = makeHashedUser(req);
        User retrievedUser = userService.registerUser(reqUser);
        return new ReadUserResDto(retrievedUser);
    }

    @GetMapping
    public List<ReadUserResDto> readUsers() {
        return userService.readUsers().stream().map(ReadUserResDto::new).toList();
    }

    @GetMapping("/id/{id}")
    public ReadUserResDto readUser(@PathVariable UUID id) {
        User retrievedUser = userService.readUser(id);
        return new ReadUserResDto(retrievedUser);
    }

    @GetMapping("/self")
    public ReadUserResDto readSelf(HttpServletRequest httpReq) {
        User jwtUser = (User) httpReq.getAttribute("user");
        return new ReadUserResDto(jwtUser);
    }

    @PutMapping("/id/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable UUID id, @RequestBody @Valid final MergeUserReqDto req) {
        User reqUser = makeHashedUser(req);
        userService.updateUser(id, reqUser);
    }

    @PutMapping("/self")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateSelf(HttpServletRequest httpReq, @RequestBody @Valid final MergeUserReqDto req) {
        User jwtUser = (User) httpReq.getAttribute("user");
        User reqUser = makeHashedUser(req);
        userService.updateUser(jwtUser, reqUser);
    }

    @DeleteMapping("/id/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(HttpServletResponse httpRes, @PathVariable UUID id) {
        userService.deleteUser(id);
    }

    @DeleteMapping("/self")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSelf(HttpServletRequest httpReq, HttpServletResponse httpRes) {
        User jwtUser = (User) httpReq.getAttribute("user");
        userService.deleteUser(httpRes, jwtUser);
    }

    private User makeHashedUser(MergeUserReqDto req) {
        String hash = passwordUtil.encode(req.getPassword());
        return User.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .passwordHash(hash)
                .build();
    }

}
