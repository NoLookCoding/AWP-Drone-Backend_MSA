package com.nolookcoding.productservice.repository;

import com.nolookcoding.productservice.dto.FiltersOfProductList;
import com.nolookcoding.productservice.dto.ProductListDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepositoryCustom {
    List<ProductListDTO> searchAllProduct(FiltersOfProductList filtersOfProductList, Pageable pageable);
}
