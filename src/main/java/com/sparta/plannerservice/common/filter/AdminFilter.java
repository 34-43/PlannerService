package com.sparta.plannerservice.common.filter;

import com.sparta.plannerservice.common.enums.PlannerRole;
import io.jsonwebtoken.lang.Strings;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Slf4j(topic = "AdminFilter")
@Component
@Order(3)
public class AdminFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 인증 필터에서 분석한 토큰 속 역할을 가져온다.
        PlannerRole role = (PlannerRole) request.getAttribute("role");

        // 현재 요청 경로를 일단 가져온다.
        String uri = request.getRequestURI();

        // 인증 필터가 단락된 경우 마찬가지로 단락
        if (Objects.isNull(role)) {
            chain.doFilter(request, response);
            return;
        }

        // admin 경로가 아니면 바로 단락
        if (Strings.hasText(uri) && !uri.startsWith("/api/admin")) {
            chain.doFilter(request, response);
            return;
        }

        // admin 역할이 맞을 경우 단락
        if (role.equals(PlannerRole.ADMIN)) {
            chain.doFilter(request, response);
            return;
        }

        throw new ServletException("Unauthorized");

    }
}
