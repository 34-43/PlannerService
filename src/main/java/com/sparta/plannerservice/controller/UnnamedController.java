package com.sparta.plannerservice.controller;

import com.sparta.plannerservice.dto.DefaultResponseDto;
import com.sparta.plannerservice.service.UnnamedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UnnamedController {
    private final UnnamedService unnamedService;

    @GetMapping("/plan")
    public DefaultResponseDto addPlan() {
        unnamedService.unnamedService();
        return null;
    }
}
