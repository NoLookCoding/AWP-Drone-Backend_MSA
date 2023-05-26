package com.nolookcoding.cartservice.controller;

import com.nolookcoding.cartservice.dto.DetailProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("product-service")
public interface ProductServiceFeignClient {

    @GetMapping("/products/{productId}")
    ResponseEntity<DetailProductDTO> loadDetailProduct(
            @PathVariable("productId") Long id
    );
}
