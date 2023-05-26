package com.nolookcoding.cartservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartRequestDTO {

    private Long productId;
    private int quantity;

    @Builder
    public CartRequestDTO(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
