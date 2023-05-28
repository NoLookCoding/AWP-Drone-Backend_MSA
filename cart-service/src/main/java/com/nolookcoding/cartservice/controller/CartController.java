package com.nolookcoding.cartservice.controller;

import com.nolookcoding.cartservice.dto.CartListDTO;
import com.nolookcoding.cartservice.dto.CartRequestDTO;
import com.nolookcoding.cartservice.dto.UpdateStockQuantityDTO;
import com.nolookcoding.cartservice.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    @PostMapping("/{userId}")
    public ResponseEntity<Map<String, Long>> createCart(@PathVariable("userId") Long userId, @RequestBody CartRequestDTO cartRequestDTO) {
        Long cartId = cartService.createCart(userId, cartRequestDTO);
        Map<String, Long> response = new HashMap<>();
        response.put("cartId", cartId);

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{cartId}/quantity")
    public ResponseEntity<Void> updateCart(@PathVariable("cartId") Long cartId, @RequestBody UpdateStockQuantityDTO updateStockQuantityDTO) {
        cartService.updateCart(cartId, updateStockQuantityDTO.getQuantity(), updateStockQuantityDTO.isAddable());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable("cartId") Long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartListDTO>> getCartsByUserId(@PathVariable("userId") Long userId) {
        List<CartListDTO> carts = cartService.getCartsByUserId(userId);
        return ResponseEntity.ok(carts);
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<Void> updateDataState(
            @PathVariable("cartId") Long cardId,
            @RequestBody Map<String, Boolean> isOrdered
    ) {
        cartService.updateDataState(cardId, isOrdered.get("isOrdered"));
        return ResponseEntity.ok().build();
    }
}
