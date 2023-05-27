package com.nolookcoding.orderservice.dto.order;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class CartOrderCreateDTO extends OrderBaseInformation {

    private List<Long> cartList;

    public CartOrderCreateDTO(String receiver, String address, String phoneNumber, List<Long> cartList, String requestOption) {
        super(receiver, address, phoneNumber, requestOption);
        this.cartList = cartList;
    }
}
