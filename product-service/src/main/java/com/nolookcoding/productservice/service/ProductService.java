package com.nolookcoding.productservice.service;

import com.nolookcoding.productservice.domain.Product;
import com.nolookcoding.productservice.dto.DetailProductDTO;
import com.nolookcoding.productservice.dto.FiltersOfProductList;
import com.nolookcoding.productservice.dto.ProductListDTO;
import com.nolookcoding.productservice.dto.ProductRegisterDTO;
import com.nolookcoding.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final HashtagService hashtagService;

    @Transactional
    public Long create(ProductRegisterDTO prDTO) throws Exception {

        Product product;
        Long productId;

        try {
            product = Product.builder()
                    .name(prDTO.getProductName())
                    .price(prDTO.getProductPrice())
                    .description(prDTO.getProductDescription())
                    .category(prDTO.getCategory())
                    .imageUrl(prDTO.getImgUrl())
                    .stockQuantity(prDTO.getStockQuantity())
                    .build();
            productId = productRepository.save(product).getId();
            if (prDTO.getHashtags() != null) {
                hashtagService.saveHashtagOfProduct(prDTO.getHashtags(), productId);
            }
        } catch (Exception e) {
            throw new Exception();
        }
        return productId;
    }

    @Transactional
    public void modify(Long productId, ProductRegisterDTO prDTO) throws Exception {
        Product product = productRepository.findById(productId).orElseThrow(Exception::new);

        try {
            product.updateProduct(prDTO.getProductName()
                    , prDTO.getProductPrice()
                    , prDTO.getProductDescription()
                    , prDTO.getStockQuantity()
                    , prDTO.getCategory()
                    , prDTO.getImgUrl());

            hashtagService.modifyHashtagOfProduct(prDTO.getHashtags(), productId);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Transactional
    public void delete(Long productId) throws Exception {
        Product product = productRepository.findById(productId).orElseThrow(Exception::new);
        productRepository.delete(product);
    }

    @Transactional
    public DetailProductDTO getDetail(Long productId) throws Exception {

        DetailProductDTO detailProductDTO;
        Product product;
        try {
            product = productRepository.findById(productId).orElseThrow(Exception::new);
            detailProductDTO = DetailProductDTO.builder().
                    productId(product.getId())
                    .productDescription(product.getDescription())
                    .productName(product.getName())
                    .productPrice(product.getPrice())
                    .stockQuantity(product.getStockQuantity())
                    .category(product.getCategory())
                    .hashtags(hashtagService.convertStringHashtags(product.getHashtags()))
                    .imgUrl(product.getImageUrl())
                    .build();
        } catch (Exception e) {
            throw new Exception();
        }
        return detailProductDTO;
    }

    @Transactional
    public Boolean isPurchasable(Long productId, int inputQuantity) throws Exception {
        Product product = productRepository.findById(productId).orElseThrow(Exception::new);

        if (product.getStockQuantity() < 1)
            return false;

        return product.getStockQuantity() - inputQuantity >= 0;
    }


    @Transactional
    public List<ProductListDTO> getAllProducts(FiltersOfProductList filtersOfProductList, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return productRepository.searchAllProduct(filtersOfProductList, pageRequest);
    }
}
