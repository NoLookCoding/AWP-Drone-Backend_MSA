package com.nolookcoding.orderservice.api;

import com.nolookcoding.orderservice.domain.OrderState;
import com.nolookcoding.orderservice.dto.cart.CartListDTO;
import com.nolookcoding.orderservice.dto.order.*;
import com.nolookcoding.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderAPI {

    private final OrderService orderService;
    private final CartServiceFeignClient cartServiceFeignClient;

    /**
     * 장바구니로 상품 주문 API
     *
     * @param cartOrderCreateDTO 주문 정보와 장바구니 정보를 담은 DTO
     * @return 생성한 주문 ID
     */
    @PostMapping("/cart")
    public ResponseEntity<Map<String, Long>> orderInCart(
            @RequestBody CartOrderCreateDTO cartOrderCreateDTO
    ) {

        ResponseEntity<List<CartListDTO>> cartsByUserId = cartServiceFeignClient.getCartsByUserId(cartOrderCreateDTO.getUserId());

        if (cartsByUserId.getStatusCode() == HttpStatus.OK) {

            if (cartsByUserId.getBody() != null) {
                Long order = orderService.createCartOrder(cartOrderCreateDTO, cartsByUserId.getBody(), cartOrderCreateDTO.getUserId());
                Map<String, Long> response = new HashMap<>();
                response.put("orderId", order);
                return ResponseEntity.ok().body(response);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 바로 구매로 상품 주문 API
     *
     * @param directOrderCreateDTO 주문 정보와 단일 상품 정보를 담은 DTO
     * @return 생성한 주문 ID
     */
    @PostMapping("/direct")
    public ResponseEntity<Map<String, Long>> orderInDirect(
            @RequestBody DirectOrderCreateDTO directOrderCreateDTO
    ) {

        Long orderId = orderService.createDirectOrder(directOrderCreateDTO, directOrderCreateDTO.getUserId());
        Map<String, Long> response = new LinkedHashMap<>();
        response.put("orderId", orderId);
        return ResponseEntity.ok().body(response);
    }

    /**
     * 특정 유저의 전체 주문 내역 조회 API
     *
     * @param userId       주문 내역을 가져올 유저 세션 ID
     * @param cursor       페이지 시작점
     * @param size         가져올 페이지 개수
     * @param isReceivable 주문 필터 옵션 (true: 주문 완료, 배송 준비 중, 배송 중 | false: 배송 완료, 취소)
     * @return 주문 리스트
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<RealOrderListDTO>> loadOrdersOfUser(
            @PathVariable("userId") Long userId,
            @RequestParam("cursor") int cursor,
            @RequestParam("size") int size,
            @RequestParam(value = "filter", required = false) Boolean isReceivable
    ) {

        List<RealOrderListDTO> orders = orderService.getAllOrders(isReceivable, cursor, size, userId);
        return ResponseEntity.ok().body(orders);
    }

    /**
     * 특정 유저의 상세 주문 정보 API
     *
     * @param orderId 주문 ID
     * @return 상세 주문 데이터 DTO
     */
    @GetMapping("/user/{orderId}")
    public ResponseEntity<DetailOrderDTO> loadDetailOrder(
            @PathVariable("orderId") Long orderId
    ) {
        DetailOrderDTO detailOrderDTO = orderService.getDetailOrderOfUser(orderId);
        return ResponseEntity.ok().body(detailOrderDTO);
    }

    /**
     * 주문 취소 API
     *
     * @param orderId 취소할 주문 ID
     * @return X
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> cancelOrder(
            @PathVariable("orderId") Long orderId
    ) {
        orderService.cancelOrder(orderId, OrderState.CANCEL);
        return ResponseEntity.ok().build();
    }

    /**
     * 주문 상태 변경 API
     *
     * @param orderId  상태 변경할 주문 ID
     * @param newState 변경하고자 하는 상태 (Json)
     * @return X
     */
    @PutMapping("/{orderId}/order-state")
    public ResponseEntity<Void> updateOrderState(
            @PathVariable("orderId") Long orderId,
            @RequestBody Map<String, String> newState
    ) {
        orderService.changeOrderState(orderId, OrderState.findCategoryByString(newState.get("orderState")));

        return ResponseEntity.ok().build();
    }

}
