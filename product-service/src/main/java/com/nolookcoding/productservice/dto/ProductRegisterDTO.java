package com.nolookcoding.productservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductRegisterDTO {

    private String productName;
    private int productPrice;
    private String productDescription;
    private int stockQuantity;
    private Long categoryId;
    private String hashtags;
    private String imgUrl;


    @Builder
    public ProductRegisterDTO(String productName, int productPrice, String productDescription, int stockQuantity, Long categoryId, String hashtags, String imgUrl) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.stockQuantity = stockQuantity;
        this.categoryId = categoryId;
        this.hashtags = hashtags;
        this.imgUrl = imgUrl;
    }
}
