package com.sparta.plannerservice.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.plannerservice.common.dto.ExceptionResDto;
import com.sparta.plannerservice.common.exception.FailedRequestException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j(topic = "ExceptionFilter")
@Component
@Order(1)
public class ExceptionFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (FailedRequestException ex) {
            HttpStatus status = ex.getFailedRequest().getStatus();

            response.setStatus(status.value());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String json = new ObjectMapper().writeValueAsString(new ExceptionResDto(status, ex.getMessage()));
            response.getWriter().write(json);
        }
    }
}
