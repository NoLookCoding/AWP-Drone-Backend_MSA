package com.nolookcoding.productservice.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int stockQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    private String hashtags;

    private String imageUrl;

    @Builder
    public Product(String name, int price, String description, int stockQuantity, Category category, String hashtags, String imageUrl) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.hashtags = hashtags;
        this.imageUrl = imageUrl;
    }

    public void updateProduct(String name, int price, String description, int stockQuantity, Category category, String hashtags, String imageUrl) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.hashtags = hashtags;
        this.imageUrl = imageUrl;
    }
}
