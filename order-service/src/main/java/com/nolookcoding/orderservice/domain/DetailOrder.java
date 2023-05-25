package com.nolookcoding.orderservice.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor
public class DetailOrder extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DETAIL_ORDER_ID")
    private Long id;

    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    private Long productId;

    @Builder
    public DetailOrder(Long id, int quantity, Order order, Long productId) {
        this.id = id;
        this.quantity = quantity;
        this.order = order;
        this.productId = productId;
    }
}
