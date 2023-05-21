package com.nolookcoding.productservice.repository;

import com.nolookcoding.productservice.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
