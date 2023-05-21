package com.nolookcoding.productservice.dto;

import com.nolookcoding.productservice.domain.Category;
import lombok.Builder;
import lombok.Data;

@Data
public class DetailProductDTO {
    private Long productId;
    private String productName;
    private int productPrice;
    private String productDescription;
    private int stockQuantity;
    private Category category;
    private String hashtags;
    private String imgUrl;

    @Builder
    public DetailProductDTO(Long productId, String productName, int productPrice, String productDescription, int stockQuantity, Category category, String hashtags, String imgUrl) {
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
