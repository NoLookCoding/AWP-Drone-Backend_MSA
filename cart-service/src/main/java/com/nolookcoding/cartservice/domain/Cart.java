package com.nolookcoding.cartservice.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
public class Cart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CART_ID")
    private Long id;

    private Long userId;

    private Long productId;

    private int quantity;

    @Builder
    public Cart(Long userId, Long productId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public void updateCartQuantity(int quantity, boolean isAddable, int currentStock) {
        if (isAddable) {
            if(currentStock >= quantity)
                this.quantity += quantity;
        }
        else if (this.quantity - quantity >= 0)
            this.quantity -= quantity;
    }

    public void setUserId(Long userId){
        this.userId = userId;
    }
}
