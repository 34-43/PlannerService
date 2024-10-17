package com.sparta.plannerservice.common.util;

import com.sparta.plannerservice.common.exception.FailedRequestException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;

public class JwtUtil {
    // Jwt 포맷 관련 상수 설정
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    private final long TOKEN_EXPIRATION_TIME = 60 * 60 * 1000L; // 토큰 만료 시간. *60(=1시간) < *60(=1분) < 1000(=1초)

    // 환경 변수로 저장된 비밀 키 문자열
    @Value("${jwt.secret.key}")
    private String keyString;

    // 동적으로 생성될 암복호화용 비밀 키
    private SecretKey key;

    // 비밀 키를 생성하는 초기화 메서드
    @PostConstruct
    public void init() {
        byte[] keyBytes = keyString.getBytes();
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 주어진 모든 값과 전달된 인자를 토대로 양식에 맞는 Jwt 토큰 문자열을 반환하는 메서드
    public String createToken(String subject) {
        Date now = new Date();

        String jwt = Jwts.builder()
                .subject(subject)
                .signWith(key)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + TOKEN_EXPIRATION_TIME))
                .compact();

        return BEARER_PREFIX + jwt;
    }

    // 입력된 token 을 입력된 응답 객체에 쿠키로 포함시키는 프로시저 메서드
    public void addJwtToCookie(String token, HttpServletResponse res) {
        // 토큰이 null 로 전달되는 경우, 인코딩을 생략하고, 곧바로 만료 될 null cookie 를 만들어 덮어씁니다.
        if (Objects.isNull(token)) {
            Cookie nullCookie = new Cookie(AUTHORIZATION_HEADER, null);
            nullCookie.setMaxAge(0);
            nullCookie.setPath("/");
            res.addCookie(nullCookie);
            return;
        }
        try {
            // 공백을 포함하여, 토큰을 인코딩 함.
            String encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8).replaceAll("\\+", "%20");

            // 헤더 형식 및 위치를 지정한 쿠키 생성
            Cookie cookie = new Cookie(AUTHORIZATION_HEADER, encodedToken);
            cookie.setPath("/");

            // 응답 객체에 쿠키 저장
            res.addCookie(cookie);

        } catch (Exception ex) {
            throw new FailedRequestException(ex.getMessage());
        }
    }

    // Bearer 토큰 형식을 확인하고 Bearer 접두어를 제거하는 메서드
    public String detachBearer(String token) {
        if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
            return token.substring(BEARER_PREFIX.length());
        } else {
            throw new FailedRequestException("not a bearer token");
        }
    }

    public Jws<Claims> validateToken(String token) {
        try {
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
        } catch (Exception ex) {
            throw new FailedRequestException(ex.getMessage());
        }
    }

    public String getSubjectFromToken(String token) {
        Jws<Claims> claimsJws = validateToken(token);
        Claims claims = claimsJws.getPayload();
        return claims.getSubject();
    }

    public String getTokenFromReq(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(AUTHORIZATION_HEADER)) {
                    try {
                        return URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                    } catch (Exception ex) {
                        throw new FailedRequestException(ex.getMessage());
                    }
                }
            }
        }
        throw new FailedRequestException("there is no authorization token");
    }
}
