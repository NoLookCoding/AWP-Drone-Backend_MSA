package com.nolookcoding.orderservice.dto.order;

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
    private List<DetailProductDTO> products;

    @Builder
    public RealOrderListDTO(Long orderId, String orderUUID, LocalDateTime createdDate, List<DetailProductDTO> products) {
        this.orderId = orderId;
        this.orderUUID = orderUUID;
        this.createdDate = createdDate;
        this.products = products;
    }
}
