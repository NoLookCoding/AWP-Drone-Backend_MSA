package com.nolookcoding.orderservice.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor
@Table(name = "Orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<DetailOrder> detailOrders;

    @Builder
    public Order(Long userId, int totalPrice, String receiver, String phoneNumber, String address, String orderUUID, String requestOption, OrderState orderState) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.receiver = receiver;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.orderUUID = orderUUID;
        this.requestOption = requestOption;
        this.orderState = orderState;
    }

    public void updateOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
