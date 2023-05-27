package com.nolookcoding.orderservice.dto.order;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class OrderBaseInformation {
    private String receiver;
    private String address;
    private String phoneNumber;
    private String requestOption;

    public OrderBaseInformation(String receiver, String address, String phoneNumber, String requestOption) {
        this.receiver = receiver;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.requestOption = requestOption;
    }
}
