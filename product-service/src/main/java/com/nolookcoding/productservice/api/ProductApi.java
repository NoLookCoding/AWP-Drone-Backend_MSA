package com.nolookcoding.productservice.api;

import com.nolookcoding.productservice.dto.ProductRegisterDTO;
import com.nolookcoding.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductApi {

    private final ProductService productService;

    @PostMapping("")
    public ResponseEntity<Map<String, Long>> createProduct(@RequestBody ProductRegisterDTO productRegisterDTO) throws Exception {

        Long productId = productService.create(productRegisterDTO);
        Map<String, Long> response = new LinkedHashMap<>();
        response.put("productId", productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Map<String, Long>> modifyProduct(@PathVariable("productId") Long productId, @RequestBody ProductRegisterDTO productRegisterDTO) throws Exception {

        productService.modify(productId, productRegisterDTO);
        Map<String, Long> response = new LinkedHashMap<>();
        response.put("productId", productId);

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") Long productId) throws Exception {
        productService.delete(productId);
        return ResponseEntity.ok().build();
    }

}
