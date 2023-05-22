package com.nolookcoding.productservice.api;

import com.nolookcoding.productservice.domain.Category;
import com.nolookcoding.productservice.domain.SortStrategy;
import com.nolookcoding.productservice.dto.*;
import com.nolookcoding.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SeungGun
 * @apiNote 상품 도메인에 대한 API 컨트롤러
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductAPI {

    private final ProductService productService;

    /**
     * 상품 등록 API
     *
     * @param productRegisterDTO 상품 등록 요청 DTO
     * @return 생성한 상품의 Primary Key
     * @throws Exception
     */
    @PostMapping("")
    public ResponseEntity<Map<String, Long>> createProduct(
            @RequestBody ProductRegisterDTO productRegisterDTO) throws Exception {

        Long productId = productService.create(productRegisterDTO);
        Map<String, Long> response = new LinkedHashMap<>();
        response.put("productId", productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 상품 수정 API
     *
     * @param productId          수정할 상품의 Primary Key
     * @param productRegisterDTO 상품 수정할 요청 DTO
     * @return 수정한 상품의 Primary Key
     * @throws Exception
     */
    @PutMapping("/{productId}")
    public ResponseEntity<Map<String, Long>> modifyProduct(
            @PathVariable("productId") Long productId,
            @RequestBody ProductRegisterDTO productRegisterDTO) throws Exception {

        productService.modify(productId, productRegisterDTO);
        Map<String, Long> response = new LinkedHashMap<>();
        response.put("productId", productId);

        return ResponseEntity.ok().body(response);
    }

    /**
     * 상품 삭제 API
     *
     * @param productId 삭제할 상품의 Primary Key
     * @return X (상태 코드로 판단)
     * @throws Exception
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable("productId") Long productId) throws Exception {
        productService.delete(productId);
        return ResponseEntity.ok().build();
    }


    /**
     * 상품 목록 조회 API
     *
     * @param cursor         페이지 시작 위치
     * @param size           가져올 페이지의 크기
     * @param sortCriteria   정렬 전략
     * @param keyword        검색할 문자열
     * @param hashtag        검색할 해시태그 문자열
     * @param categoryFilter 필터링할 카테고리 문자열
     * @return 페이징, 정렬, 검색, 필터링을 적용한 기본 데이터가 담긴 DTO 리스트
     */
    @GetMapping("")
    public ResponseEntity<List<ProductListDTO>> loadProductList(
            @RequestParam(value = "cursor") int cursor,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort", required = false) String sortCriteria,
            @RequestParam(value = "search", required = false) String keyword,
            @RequestParam(value = "hashtag", required = false) String hashtag,
            @RequestParam(value = "filter", required = false) String categoryFilter
    ) {

        FiltersOfProductList filtersOfProductList = FiltersOfProductList.builder()
                .searchHashtags(hashtag)
                .searchKeyword(keyword)
                .sortStrategy(SortStrategy.findStrategyByString(sortCriteria))
                .categoryFilter(Category.findCategoryByString(categoryFilter))
                .build();

        List<ProductListDTO> allProducts = productService.getAllProducts(filtersOfProductList, cursor, size);

        return ResponseEntity.ok().body(allProducts);
    }

    /**
     * 상세 상품 조회 API
     *
     * @param productId 상세 조회할 상품의 Primary Key
     * @return 상품의 상세 정보 응답 DTO
     */
    @GetMapping("/{productId}")
    public ResponseEntity<DetailProductDTO> loadDetailProduct(
            @PathVariable("productId") Long productId) throws Exception {
        DetailProductDTO detail = productService.getDetail(productId);
        return ResponseEntity.ok().body(detail);
    }

    /**
     * 상품 재고 검증을 통한 상품 구매 가능 여부 체크 API
     *
     * @param productId        재고 검증할 상품의 Primary Key
     * @param stockQuantityDTO 검증을 위한 구매 예정 수량
     * @return X (상태 코드로 판단) 구매 가능하면 OK, 아니라면 FORBIDDEN
     * @throws Exception
     */
    @PostMapping("/{productId}/stock-quantity")
    public ResponseEntity<Void> validateStockQuantity(
            @PathVariable("productId") Long productId,
            @RequestBody StockQuantityDTO stockQuantityDTO) throws Exception {
        Boolean purchasable = productService.isPurchasable(productId, stockQuantityDTO.getInputStockQuantity());

        return ResponseEntity.status(purchasable ? HttpStatus.OK : HttpStatus.FORBIDDEN).build();
    }

}
