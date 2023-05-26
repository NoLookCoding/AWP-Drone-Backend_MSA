package com.nolookcoding.orderservice.api;

import com.nolookcoding.orderservice.dto.product.DetailProductDTO;
import com.nolookcoding.orderservice.dto.product.StockQuantityDTO;
import com.nolookcoding.orderservice.dto.product.UpdateStockQuantityDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient("product-service")
public interface ProductServiceFeignClient {

    @GetMapping("/products/{productId}")
    ResponseEntity<DetailProductDTO> loadDetailProduct(
            @PathVariable("productId") Long id
    );

    @PostMapping("/products/{productId}/stock-quantity/validate")
    ResponseEntity<Void> validateStockQuantity(
            @PathVariable("productId") Long id,
            @RequestBody StockQuantityDTO stockQuantityDTO
    );

    @PutMapping("/products/{productId}/stock-quantity")
    ResponseEntity<Void> updateStockQuantity(
            @PathVariable("productId") Long productId,
            @RequestBody UpdateStockQuantityDTO updateStockQuantityDTO
    );
}
