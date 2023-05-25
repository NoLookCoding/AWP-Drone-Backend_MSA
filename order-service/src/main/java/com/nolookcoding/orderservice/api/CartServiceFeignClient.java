package com.nolookcoding.orderservice.api;

import com.nolookcoding.orderservice.dto.cart.CartListDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("cart-service")
public interface CartServiceFeignClient {

    @GetMapping("/carts/{userId}")
    ResponseEntity<List<CartListDTO>> getCartsByUserId(@PathVariable("userId") Long userId);
}
