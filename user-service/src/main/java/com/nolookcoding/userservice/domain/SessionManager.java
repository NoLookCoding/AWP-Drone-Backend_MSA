package com.nolookcoding.userservice.domain;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    private static Map<String, Long> store = new ConcurrentHashMap<>();

    public String createSession(User user) {
        String token = UUID.randomUUID().toString();
        store.put(token, user.getId());
        return token;
    }

    public Long getSession(String value) {
        return findValue(value);
    }

    public Long findValue(String value) {
        if (store.containsKey(value)) {
            return store.get(value);
        }
        return null;
    }

    public void sessionExpire(String value) {
        store.remove(value);
        System.out.println("세션 삭제!");
    }
}
