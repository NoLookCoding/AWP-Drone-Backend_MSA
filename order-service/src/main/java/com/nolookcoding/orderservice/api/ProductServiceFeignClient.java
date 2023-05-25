package com.nolookcoding.orderservice.api;

import com.nolookcoding.orderservice.dto.product.DetailProductDTO;
import com.nolookcoding.orderservice.dto.product.StockQuantityDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("product-service")
public interface ProductServiceFeignClient {

    @GetMapping("/products/{productId}")
    ResponseEntity<DetailProductDTO> loadDetailProduct(
            @PathVariable("productId") Long id
    );

    @PostMapping("/products/{productId}/stock-quantity")
    ResponseEntity<Void> validateStockQuantity(
            @PathVariable("productId") Long id,
            @RequestBody StockQuantityDTO stockQuantityDTO
    );

}
