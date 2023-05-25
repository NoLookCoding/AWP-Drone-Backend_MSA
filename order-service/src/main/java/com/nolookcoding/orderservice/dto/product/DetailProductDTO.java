package com.nolookcoding.orderservice.dto.product;

import com.nolookcoding.orderservice.domain.Category;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class DetailProductDTO {
    private Long productId;
    private String productName;
    private int productPrice;
    private String productDescription;
    private int stockQuantity;
    private Category category;
    private List<String> hashtags = new ArrayList<>();
    private String imgUrl;

    @Builder
    public DetailProductDTO(Long productId, String productName, int productPrice, String productDescription, int stockQuantity, Category category, List<String> hashtags, String imgUrl) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.hashtags = hashtags;
        this.imgUrl = imgUrl;
    }
}
