package com.nolookcoding.productservice.dto;

import com.nolookcoding.productservice.domain.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ProductRegisterDTO {

    private String productName;
    private int productPrice;
    private String productDescription;
    private int stockQuantity;
    private Category category;
    private List<String> hashtags = new ArrayList<>();
    private String imgUrl;


    @Builder
    public ProductRegisterDTO(String productName, int productPrice, String productDescription, int stockQuantity, Category category, List<String> hashtags, String imgUrl) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.hashtags = hashtags;
        this.imgUrl = imgUrl;
    }
}
