package com.nolookcoding.orderservice.dto.order;

import com.nolookcoding.orderservice.domain.OrderState;
import com.nolookcoding.orderservice.dto.product.DetailProductDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class RealOrderListDTO {

    private Long orderId;
    private String orderUUID;
    private LocalDateTime createdDate;
    private OrderState orderState;
    private List<DetailProductDTO> products;

    @Builder
    public RealOrderListDTO(Long orderId, OrderState orderState, String orderUUID, LocalDateTime createdDate, List<DetailProductDTO> products) {
        this.orderId = orderId;
        this.orderState = orderState;
        this.orderUUID = orderUUID;
        this.createdDate = createdDate;
        this.products = products;
    }
}
