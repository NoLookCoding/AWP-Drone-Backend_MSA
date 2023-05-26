package com.nolookcoding.productservice.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Hashtag> hashtags = new ArrayList<>();

    private String imageUrl;

    @Builder
    public Product(String name, int price, String description, int stockQuantity, Category category, String imageUrl) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public void updateProduct(String name, int price, String description, int stockQuantity, Category category, String imageUrl) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public void updateStockQuantity(int quantity, boolean isAddable) {
        if (isAddable)
            this.stockQuantity += quantity;
        else if (this.stockQuantity - quantity >= 0)
            this.stockQuantity -= quantity;
    }
}
