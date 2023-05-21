package com.nolookcoding.productservice.service;

import com.nolookcoding.productservice.domain.Category;
import com.nolookcoding.productservice.domain.Product;
import com.nolookcoding.productservice.dto.ProductRegisterDTO;
import com.nolookcoding.productservice.repository.CategoryRepository;
import com.nolookcoding.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Long create(ProductRegisterDTO prDTO) throws Exception {

        Category category = categoryRepository.findById(prDTO.getCategoryId()).orElseThrow(Exception::new);

        Product product;
        Long productId;

        try {
            product = Product.builder()
                    .name(prDTO.getProductName())
                    .price(prDTO.getProductPrice())
                    .description(prDTO.getProductDescription())
                    .category(category)
                    .imageUrl(prDTO.getImgUrl())
                    .stockQuantity(prDTO.getStockQuantity())
                    .hashtags(prDTO.getHashtags())
                    .build();
            productId = productRepository.save(product).getId();
        } catch (Exception e) {
            throw new Exception();
        }
        return productId;
    }

    @Transactional
    public void modify(Long productId, ProductRegisterDTO prDTO) throws Exception {
        Product product = productRepository.findById(productId).orElseThrow(Exception::new);
        Category category = categoryRepository.findById(prDTO.getCategoryId()).orElseThrow(Exception::new);

        try {
            product.updateProduct(prDTO.getProductName(), prDTO.getProductPrice(), prDTO.getProductDescription(), prDTO.getStockQuantity(), category, prDTO.getHashtags(), prDTO.getImgUrl());
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Transactional
    public void delete(Long productId) throws Exception {
        Product product = productRepository.findById(productId).orElseThrow(Exception::new);
        productRepository.delete(product);
    }
}
