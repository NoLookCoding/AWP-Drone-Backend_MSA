package com.nolookcoding.orderservice.dto.order;

import com.nolookcoding.orderservice.domain.OrderState;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class DetailOrderDTO {

    private Long orderId;
    private String orderUUID;
    private OrderState orderState;
    private LocalDateTime orderDate;
    private String address;
    private Long userId;
    private String receiver;
    private String requestOption;
    private List<OrderProductDTO> orderedProducts = new ArrayList<>();

    @Builder
    public DetailOrderDTO(Long orderId, String orderUUID, OrderState orderState, LocalDateTime orderDate, String address, Long userId, String receiver, String requestOption, List<OrderProductDTO> orderedProducts) {
        this.orderId = orderId;
        this.orderUUID = orderUUID;
        this.orderState = orderState;
        this.orderDate = orderDate;
        this.address = address;
        this.userId = userId;
        this.receiver = receiver;
        this.requestOption = requestOption;
        this.orderedProducts = orderedProducts;
    }
}
