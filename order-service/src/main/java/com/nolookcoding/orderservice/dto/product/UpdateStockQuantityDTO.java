package com.nolookcoding.orderservice.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateStockQuantityDTO {

    private int quantity;
    @JsonProperty(value = "isAddable")
    private boolean isAddable;

    @Builder
    public UpdateStockQuantityDTO(int quantity, boolean isAddable) {
        this.quantity = quantity;
        this.isAddable = isAddable;
    }
}