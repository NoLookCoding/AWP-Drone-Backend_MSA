package com.nolookcoding.productservice.repository;

import com.nolookcoding.productservice.domain.Hashtag;
import com.nolookcoding.productservice.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    void deleteAllByProduct(Product product);
}
