package com.nolookcoding.userservice.domain;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    private static Map<String, String> store = new ConcurrentHashMap<>();

    public void createSession(String value, HttpServletResponse response) {
        String token = UUID.randomUUID().toString();
        store.put(token, value);
        Cookie cookie = new Cookie(SessionConst.sessionId, token);
        response.addCookie(cookie);
    }

    public String getSession(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request);

        if (sessionCookie == null) {
            return null;
        }

        return store.get(sessionCookie.getValue());
    }

    public Cookie findCookie(HttpServletRequest request) {
        //request 요청에 cookie가 없을 경우 null
        if (request.getCookies() == null) {
            return null;
        }

        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(SessionConst.sessionId))
                .findFirst()
                .orElse(null);
    }

    public void sessionExpire(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request);
        if (sessionCookie != null) {
            store.remove(sessionCookie.getValue());
        }
    }
}

