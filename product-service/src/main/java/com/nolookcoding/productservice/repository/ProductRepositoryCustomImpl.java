package com.nolookcoding.productservice.repository;

import com.nolookcoding.productservice.domain.Category;
import com.nolookcoding.productservice.domain.Product;
import com.nolookcoding.productservice.domain.SortStrategy;
import com.nolookcoding.productservice.dto.FiltersOfProductList;
import com.nolookcoding.productservice.dto.ProductListDTO;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.nolookcoding.productservice.domain.QHashtag.hashtag;
import static com.nolookcoding.productservice.domain.QProduct.product;

/**
 * 상품 목록 조회 시 동적 쿼리를 위한 QueryDsl
 *
 * @author SeungGun
 */
@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ProductListDTO> searchAllProduct(FiltersOfProductList filtersOfProductList, Pageable pageable) {
        List<Product> result = queryFactory
                .selectFrom(product)
                .where(
                        searchKeyword(filtersOfProductList.getSearchKeyword()),
                        searchHashtags(filtersOfProductList.getSearchHashtags()),
                        filterCategory(filtersOfProductList.getCategoryFilter())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(sortProduct(filtersOfProductList.getSortStrategy()))
                .fetch();

        return result.stream().map(r -> ProductListDTO.builder()
                .productId(r.getId())
                .productName(r.getName())
                .price(r.getPrice())
                .category(r.getCategory())
                .imgUrl(r.getImageUrl()).build()).toList();
    }

    private Predicate searchKeyword(String keyword) {
        if (keyword == null)
            return null;
        else
            return ExpressionUtils.or(product.name.contains(keyword), product.description.contains(keyword));
    }

    private Predicate searchHashtags(String hashtagToSearch) {
        if (hashtagToSearch == null)
            return null;
        else {
            List<Long> productIds = queryFactory
                    .select(hashtag.product.id)
                    .from(hashtag)
                    .where(hashtag.content.eq(hashtagToSearch))
                    .groupBy(hashtag.product.id)
                    .fetch();
            return product.id.in(productIds);
        }
    }

    private Predicate filterCategory(Category category) {
        if (category == null)
            return null;

        switch (category) {
            case FILM -> product.category.eq(Category.FILM);
            case PERFORMANCE -> product.category.eq(Category.PERFORMANCE);
            case RECONNAISSANCE -> product.category.eq(Category.RECONNAISSANCE);
            case DISTRIBUTION -> product.category.eq(Category.DISTRIBUTION);
            case ATTACK -> product.category.eq(Category.ATTACK);
            case MANAGE -> product.category.eq(Category.MANAGE);
        }
        return null;
    }

    private OrderSpecifier<?> sortProduct(SortStrategy sortStrategy) {
        if (sortStrategy == null) {
            return new OrderSpecifier<>(Order.DESC, product.id);
        }

        switch (sortStrategy) {
            case NAME -> new OrderSpecifier<>(Order.ASC, product.name);
            case PRICE -> new OrderSpecifier<>(Order.ASC, product.price);
            case CHRONOLOGICAL -> new OrderSpecifier<>(Order.DESC, product.id);
        }
        return new OrderSpecifier<>(Order.DESC, product.id);
    }

}
