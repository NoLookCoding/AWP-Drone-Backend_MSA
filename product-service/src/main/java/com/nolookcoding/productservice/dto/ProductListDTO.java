package com.nolookcoding.productservice.dto;

import com.nolookcoding.productservice.domain.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductListDTO {

    private Long productId;
    private String productName;
    private int price;
    private String imgUrl;
    private Category category;

    @Builder
    public ProductListDTO(Long productId, String productName, int price, String imgUrl, Category category) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.imgUrl = imgUrl;
        this.category = category;
    }
}
