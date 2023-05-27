package com.nolookcoding.cartservice.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("user-service")
public interface UserServiceFeignClient {

    @PostMapping("/users/session-check")
    ResponseEntity<Long> sessionCheck(@RequestHeader("JSESSIONID") String value);
}
