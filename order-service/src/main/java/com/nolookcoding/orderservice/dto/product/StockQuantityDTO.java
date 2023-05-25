package com.nolookcoding.orderservice.dto.product;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockQuantityDTO {
    private int inputStockQuantity;

    @Builder
    public StockQuantityDTO(int inputStockQuantity) {
        this.inputStockQuantity = inputStockQuantity;
    }
}
