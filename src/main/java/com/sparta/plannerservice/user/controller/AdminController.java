package com.sparta.plannerservice.user.controller;

import com.sparta.plannerservice.user.dto.MergeUserReqDto;
import com.sparta.plannerservice.user.dto.ReadUserResDto;
import com.sparta.plannerservice.user.entity.User;
import com.sparta.plannerservice.user.service.AdminService;
import com.sparta.plannerservice.user.util.UserFactory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final UserFactory userFactory;

    @GetMapping("/id/{id}")
    public ResponseEntity<ReadUserResDto> readUser(@PathVariable UUID id) {
        User retrievedUser = adminService.readUser(id);
        return ResponseEntity.ok(new ReadUserResDto(retrievedUser));
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable UUID id, @RequestBody @Valid final MergeUserReqDto req) {
        User reqUser = userFactory.createUser(req);
        adminService.updateUser(id, reqUser);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        adminService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
