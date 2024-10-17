package com.sparta.plannerservice.common.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    // 단방향 해싱 알고리즘으로 암호 문자열 -> 해시 결과 문자열 변환을 수행하는 유틸
    public String encode(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // 단방향 해싱 알고리즘으로 입력 암호의 해시 결과와 저장된 해시의 일치 여부를 반환하는 유틸
    public boolean matches(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
