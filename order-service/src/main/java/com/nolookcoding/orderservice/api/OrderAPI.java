package com.nolookcoding.orderservice.api;

import com.nolookcoding.orderservice.dto.cart.CartListDTO;
import com.nolookcoding.orderservice.dto.order.CartOrderCreateDTO;
import com.nolookcoding.orderservice.dto.order.DirectOrderCreateDTO;
import com.nolookcoding.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderAPI {

    private final OrderService orderService;
    private final ProductServiceFeignClient productServiceFeignClient;
    private final UserServiceFeignClient userServiceFeignClient;
    private final CartServiceFeignClient cartServiceFeignClient;

//    @PostMapping("/cart")
//    public ResponseEntity<Map<String, Long>> orderInCart(
//            @RequestBody CartOrderCreateDTO cartOrderCreateDTO
//            ){
//
//        ResponseEntity<List<CartListDTO>> cartsByUserId = cartServiceFeignClient.getCartsByUserId(cartOrderCreateDTO.getUserId());
//
//
//    }

    @PostMapping("/direct")
    public ResponseEntity<Map<String, Long>> orderInDirect(
            @RequestBody DirectOrderCreateDTO directOrderCreateDTO
            ){

//        ResponseEntity<DetailProductDTO> response = productServiceFeign.loadDetailProduct(directOrderCreateDTO.getProductId());
//        if(response.getStatusCode() == HttpStatus.OK){
//
//
//        }
        Long orderId = orderService.createDirectOrder(directOrderCreateDTO);
        Map<String, Long> response = new LinkedHashMap<>();
        response.put("orderId", orderId);
        return ResponseEntity.ok().body(response);
    }

//    @GetMapping("/{userId}")
//    public ResponseEntity<Void> loadOrdersOfUser(
//            @PathVariable("userId") Long id,
//            @RequestParam("cursor") int cursor,
//            @RequestParam("size") int size,
//            @RequestParam("filter") boolean isReceivable
//    ){
//
//    }
//
//    @GetMapping("/{orderId}")
//    public ResponseEntity<Void> loadDetailOrder(
//            @PathVariable("orderId") Long id
//    ){
//
//    }
//
//    @DeleteMapping("/{orderId}")
//    public ResponseEntity<Void> cancelOrder(
//            @PathVariable("orderId") Long id
//    ){
//
//    }
}
