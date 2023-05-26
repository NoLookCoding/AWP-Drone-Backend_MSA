package com.nolookcoding.orderservice.dto.order;

import com.nolookcoding.orderservice.domain.Category;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderProductDTO {

    private Long productId;
    private String productName;
    private int price;
    private int quantity;
    private Category category;
    private List<String> hashtags = new ArrayList<>();

    @Builder
    public OrderProductDTO(Long productId, String productName, int price, int quantity, Category category, List<String> hashtags) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.hashtags = hashtags;
    }
}
