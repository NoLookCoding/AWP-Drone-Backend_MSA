package com.nolookcoding.productservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class CategoryDTO {

    private Long categoryId;
    private String categoryName;
    private String categoryDescription;

    @Builder
    public CategoryDTO(Long categoryId, String categoryName, String categoryDescription) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
    }
}
