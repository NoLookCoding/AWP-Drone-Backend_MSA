package com.nolookcoding.productservice.dto;

import com.nolookcoding.productservice.domain.Category;
import com.nolookcoding.productservice.domain.SortStrategy;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FiltersOfProductList {

    private String searchKeyword;
    private String searchHashtags;
    private SortStrategy sortStrategy;
    private Category categoryFilter;

    @Builder
    public FiltersOfProductList(String searchKeyword, String searchHashtags, SortStrategy sortStrategy, Category categoryFilter) {
        this.searchKeyword = searchKeyword;
        this.searchHashtags = searchHashtags;
        this.sortStrategy = sortStrategy;
        this.categoryFilter = categoryFilter;
    }
}
