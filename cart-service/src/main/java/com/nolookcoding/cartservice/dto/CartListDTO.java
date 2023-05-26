package com.nolookcoding.cartservice.dto;

import com.nolookcoding.cartservice.domain.Category;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartListDTO {

    private Long cartId;
    private Long productId;
    private String productName;
    private int price;
    private Category category;
    private String imgUrl;
    private int quantity;

    @Builder
    public CartListDTO(Long cartId, Long productId, String productName, int price, Category category, String imgUrl, int quantity) {
        this.cartId = cartId;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.imgUrl = imgUrl;
        this.quantity = quantity;
    }
}
