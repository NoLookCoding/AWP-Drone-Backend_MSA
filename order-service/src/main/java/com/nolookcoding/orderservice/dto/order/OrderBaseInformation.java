package com.nolookcoding.orderservice.dto.order;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class OrderBaseInformation {
    private Long userId;
    private int totalPrice;
    private String receiver;
    private String address;
    private String phoneNumber;
    private String requestOption;

    public OrderBaseInformation(Long userId, int totalPrice, String receiver, String address, String phoneNumber, String requestOption) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.receiver = receiver;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.requestOption = requestOption;
    }
}
