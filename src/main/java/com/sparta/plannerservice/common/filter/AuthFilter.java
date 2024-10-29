package com.sparta.plannerservice.common.filter;

import com.sparta.plannerservice.common.enums.PlannerRole;
import com.sparta.plannerservice.common.exception.IdNotFoundException;
import com.sparta.plannerservice.common.util.JwtUtil;
import com.sparta.plannerservice.user.entity.User;
import com.sparta.plannerservice.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.UUID;

@Slf4j(topic = "AuthFilter")
@Component
@Order(2)
@RequiredArgsConstructor
public class AuthFilter extends HttpFilter {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String url = request.getRequestURI();

        // Jwt 로 로그인 상태를 체크하지 않는 도메인은 다음으로 체인
        if (StringUtils.hasText(url)
                && (
                        url.startsWith("/api/auth/login") ||  // 로그인
                    (url.startsWith("/api/users") && !url.startsWith("/api/users/self"))    // 회원가입, 회원조회
            )
        ) {
            chain.doFilter(request, response);
        } else {
            String token = jwtUtil.getTokenFromReq(request);
            // 토큰을 Cookie 에서 찾은 경우
            if (StringUtils.hasText(token)) {
                String coreToken = jwtUtil.detachBearer(token);
                Claims claims = jwtUtil.getClaimsFromToken(coreToken);
                String sub = claims.getSubject();
                UUID subUuid = UUID.fromString(sub);
                PlannerRole role = PlannerRole.valueOf((String) claims.get("role"));
                User user = userRepository.findById(subUuid).orElseThrow(() -> new IdNotFoundException(User.class, subUuid));
                request.setAttribute("user", user);
                request.setAttribute("role", role);
                chain.doFilter(request, response);
            }
        }
    }
}
