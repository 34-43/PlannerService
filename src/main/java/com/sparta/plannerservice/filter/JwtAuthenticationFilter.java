package com.sparta.plannerservice.filter;

import com.sparta.plannerservice.common.exception.IdNotFoundException;
import com.sparta.plannerservice.common.util.JwtUtil;
import com.sparta.plannerservice.user.entity.User;
import com.sparta.plannerservice.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.UUID;

@Slf4j(topic = "JwtAuthenticationFilter")
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends HttpFilter {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String url = request.getRequestURI();

        // Jwt 로 로그인 상태를 체크하지 않는 도메인은 다음으로 체인
        if (StringUtils.hasText(url)
                && (
                        url.startsWith("/api/auth") ||
                    (url.startsWith("/api/user") && !url.startsWith("/api/user/self"))
            )
        ) {
            chain.doFilter(request, response);
        } else {
            String token = jwtUtil.getTokenFromReq(request);
            // 토큰을 Cookie 에서 찾은 경우
            if (StringUtils.hasText(token)) {
                String coreToken = jwtUtil.detachBearer(token);
                String sub = jwtUtil.getSubjectFromToken(coreToken);
                User user = userRepository.findById(UUID.fromString(sub)).orElseThrow(() -> new IdNotFoundException(User.class, UUID.fromString(sub)));
                request.setAttribute("user", user);
                chain.doFilter(request, response);
            }
        }
    }
}
