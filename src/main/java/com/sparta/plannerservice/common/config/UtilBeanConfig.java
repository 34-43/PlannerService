package com.sparta.plannerservice.common.config;

import com.sparta.plannerservice.common.util.JwtUtil;
import com.sparta.plannerservice.common.util.PasswordUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 본래 @Component 로 지정(혹은 static 정의)할 수 있는 유틸 클래스를, @Configuration 을 이용하여 Bean 직접 지정을 해보기 위해서 만든 클래스.

@Configuration
public class UtilBeanConfig {
    @Bean
    public PasswordUtil passwordUtil() {
        return new PasswordUtil();
    }

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }
}
