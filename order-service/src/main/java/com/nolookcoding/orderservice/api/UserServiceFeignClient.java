package com.nolookcoding.orderservice.api;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("user-service")
public interface UserServiceFeignClient {
}
