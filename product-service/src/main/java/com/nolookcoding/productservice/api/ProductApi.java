package com.nolookcoding.productservice.api;

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
public class ProductApi {

    private final ProductService productService;

    /**
     * 상품 등록 API
     *
     * @param productRegisterDTO
     * @return
     * @throws Exception
     */
    @PostMapping("")
    public ResponseEntity<Map<String, Long>> createProduct(@RequestBody ProductRegisterDTO productRegisterDTO) throws Exception {

        Long productId = productService.create(productRegisterDTO);
        Map<String, Long> response = new LinkedHashMap<>();
        response.put("productId", productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 상품 수정 API
     *
     * @param productId
     * @param productRegisterDTO
     * @return
     * @throws Exception
     */
    @PutMapping("/{productId}")
    public ResponseEntity<Map<String, Long>> modifyProduct(@PathVariable("productId") Long productId, @RequestBody ProductRegisterDTO productRegisterDTO) throws Exception {

        productService.modify(productId, productRegisterDTO);
        Map<String, Long> response = new LinkedHashMap<>();
        response.put("productId", productId);

        return ResponseEntity.ok().body(response);
    }

    /**
     * 상품 삭제 API
     *
     * @param productId
     * @return
     * @throws Exception
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") Long productId) throws Exception {
        productService.delete(productId);
        return ResponseEntity.ok().build();
    }

    /**
     * 상품 목록 조회 API
     *
     * @param cursor
     * @param size
     * @param sortCriteria
     * @param isAsc
     * @param filterOption
     * @return
     */
//    @GetMapping("")
//    public ResponseEntity<List<ProductListDTO>> loadProductList(
//            @RequestParam(value = "cursor") Long cursor,
//            @RequestParam(value = "size") Long size,
//            @RequestParam(value = "sort") String sortCriteria,
//            @RequestParam(value = "asc") boolean isAsc,
//            @RequestParam(value = "filter") String filterOption
//    ) {
//
//    }

    /**
     * 상세 상품 조회 API
     *
     * @param productId
     * @return
     */
    @GetMapping("/{productId}")
    public ResponseEntity<DetailProductDTO> loadDetailProduct(@PathVariable("productId") Long productId) throws Exception {
        DetailProductDTO detail = productService.getDetail(productId);
        return ResponseEntity.ok().body(detail);
    }

    /**
     * 상품 재고 검증을 통한 상품 구매 가능 여부 체크 API
     * @param productId
     * @param stockQuantityDTO
     * @return
     * @throws Exception
     */
    @PostMapping("/{productId}/stock-quantity")
    public ResponseEntity<Void> validateStockQuantity(@PathVariable("productId") Long productId, @RequestBody StockQuantityDTO stockQuantityDTO) throws Exception {
        Boolean purchasable = productService.isPurchasable(productId, stockQuantityDTO.getInputStockQuantity());

        return ResponseEntity.status(purchasable ? HttpStatus.OK : HttpStatus.FORBIDDEN).build();
    }

    /**
     * 전체 카테고리 리스트 조회 API
     * @return
     * @throws Exception
     */
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> loadCategories() throws Exception {
        List<CategoryDTO> categoryList = productService.getCategoryList();
        return ResponseEntity.ok().body(categoryList);
    }

}
