package com.nolookcoding.orderservice.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "Orders")
public class Order extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long id;

    private Long userId;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    private String receiver;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String orderUUID;

    private String requestOption;

    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    @Builder
    public Order(Long id, Long userId, int totalPrice, String receiver, String phoneNumber, String address, String orderUUID, String requestOption, OrderState orderState) {
        this.id = id;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.receiver = receiver;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.orderUUID = orderUUID;
        this.requestOption = requestOption;
        this.orderState = orderState;
    }
}
