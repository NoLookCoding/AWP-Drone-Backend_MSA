package com.nolookcoding.productservice.service;

import com.nolookcoding.productservice.domain.Hashtag;
import com.nolookcoding.productservice.domain.Product;
import com.nolookcoding.productservice.repository.HashtagRepository;
import com.nolookcoding.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HashtagService {

    private final HashtagRepository hashtagRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void saveHashtagOfProduct(List<String> hashtags, Long productId) throws Exception {
        Product product = productRepository.findById(productId).orElseThrow(Exception::new);

        if (hashtags != null) {
            for (String hashtag : hashtags) {
                Hashtag ht = Hashtag.builder()
                        .content(hashtag)
                        .product(product)
                        .build();
                hashtagRepository.save(ht);
            }
        }
    }

    @Transactional
    public void modifyHashtagOfProduct(List<String> hashtags, Long productId) throws Exception {

        Product product = productRepository.findById(productId).orElseThrow(Exception::new);
        hashtagRepository.deleteAllByProduct(product);

        saveHashtagOfProduct(hashtags, productId);
    }

    public List<String> convertStringHashtags(List<Hashtag> hashtags) {
        return hashtags.stream().map(Hashtag::getContent).toList();
    }

}
