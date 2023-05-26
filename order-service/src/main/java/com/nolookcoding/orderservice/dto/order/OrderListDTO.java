package com.nolookcoding.orderservice.dto.order;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OrderListDTO {

    private Long orderId;
    private String orderUUID;
    private Long productId;
    private LocalDateTime orderDate;

    @Builder
    public OrderListDTO(Long orderId, String orderUUID, Long productId, LocalDateTime orderDate) {
        this.orderId = orderId;
        this.orderUUID = orderUUID;
        this.productId = productId;
        this.orderDate = orderDate;
    }
}
