package com.nolookcoding.orderservice.dto.order;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class DirectOrderCreateDTO extends OrderBaseInformation {
    private Long productId;
    private int quantity;

    public DirectOrderCreateDTO(Long userId, int totalPrice, String receiver, String address, String phoneNumber, Long productId, int quantity, String requestOption) {
        super(userId, totalPrice, receiver, address, phoneNumber, requestOption);
        this.productId = productId;
        this.quantity = quantity;
    }
}
