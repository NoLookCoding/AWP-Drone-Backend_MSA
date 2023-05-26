package com.nolookcoding.orderservice.service;

import com.nolookcoding.orderservice.domain.DetailOrder;
import com.nolookcoding.orderservice.domain.Order;
import com.nolookcoding.orderservice.domain.OrderState;
import com.nolookcoding.orderservice.dto.cart.CartListDTO;
import com.nolookcoding.orderservice.dto.order.*;
import com.nolookcoding.orderservice.dto.product.DetailProductDTO;
import com.nolookcoding.orderservice.dto.product.UpdateStockQuantityDTO;
import com.nolookcoding.orderservice.repository.DetailOrderRepository;
import com.nolookcoding.orderservice.repository.OrderRepository;
import com.nolookcoding.orderservice.api.ProductServiceFeignClient;
import com.nolookcoding.orderservice.api.CartServiceFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final DetailOrderRepository detailOrderRepository;
    private final ProductServiceFeignClient productServiceFeignClient;

    private final CartServiceFeignClient cartServiceFeignClient;

    @Transactional
    public Long createDirectOrder(DirectOrderCreateDTO directOrderCreateDTO) {
        ResponseEntity<DetailProductDTO> response = productServiceFeignClient.loadDetailProduct(directOrderCreateDTO.getProductId());
        int totalPrice = -1;
        if (response.getStatusCode() == HttpStatus.OK) {
            DetailProductDTO body = response.getBody();
            if (body != null) {
                if (body.getStockQuantity() >= response.getBody().getStockQuantity()) {
                    totalPrice = body.getProductPrice() * directOrderCreateDTO.getQuantity();
                } else
                    return null;
            }
        } else {
            return null;
        }
        Order order = Order.builder()
                .orderUUID(generateRandomUID())
                .address(directOrderCreateDTO.getAddress())
                .receiver(directOrderCreateDTO.getReceiver())
                .orderState(OrderState.PAID)
                .phoneNumber(directOrderCreateDTO.getPhoneNumber())
                .requestOption(directOrderCreateDTO.getRequestOption())
                .userId(directOrderCreateDTO.getUserId())
                .totalPrice(totalPrice)
                .build();

        DetailOrder detailOrder = DetailOrder.builder()
                .quantity(directOrderCreateDTO.getQuantity())
                .productId(directOrderCreateDTO.getProductId())
                .order(order)
                .build();

        productServiceFeignClient.updateStockQuantity(
                directOrderCreateDTO.getProductId(),
                UpdateStockQuantityDTO.builder()
                        .quantity(directOrderCreateDTO.getQuantity())
                        .isAddable(false)
                        .build());

        Long orderId = orderRepository.save(order).getId();
        detailOrderRepository.save(detailOrder);

        return orderId;
    }

    @Transactional
    public Long createCartOrder(CartOrderCreateDTO cartOrderCreateDTO, List<CartListDTO> cartListDTO) {
        Order order = Order.builder()
                .orderUUID(generateRandomUID())
                .address(cartOrderCreateDTO.getAddress())
                .receiver(cartOrderCreateDTO.getReceiver())
                .orderState(OrderState.PAID)
                .phoneNumber(cartOrderCreateDTO.getPhoneNumber())
                .requestOption(cartOrderCreateDTO.getRequestOption())
                .userId(cartOrderCreateDTO.getUserId())
                .build();

        int totalPrice = 0;
        Long orderId = orderRepository.save(order).getId();

        for (CartListDTO c : cartListDTO) {
            detailOrderRepository.save(DetailOrder.builder()
                    .quantity(c.getQuantity())
                    .productId(c.getProductId())
                    .order(order)
                    .dataState("CART " + c.getCartId())
                    .build());

            totalPrice += c.getPrice() * c.getQuantity();

            Map<String, Boolean> state = new LinkedHashMap<>();
            state.put("isOrdered", false);

            // 각 장바구니 데이터마다 주문이 완료되었다는 데이터 상태 변경
            ResponseEntity<Void> response = cartServiceFeignClient.updateDataState(c.getCartId(), state);

            productServiceFeignClient.updateStockQuantity(
                    c.getProductId(),
                    UpdateStockQuantityDTO.builder()
                            .quantity(c.getQuantity())
                            .isAddable(false)
                            .build());
        }
        if (totalPrice > 0)
            order.setTotalPrice(totalPrice);

        return orderId;
    }

    private String generateRandomUID() {
        return UUID.randomUUID().toString();
    }

    @Transactional
    public List<RealOrderListDTO> getAllOrders(Boolean isReceivable, int page, int size, Long userId) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Order> orders = orderRepository.searchAllOrdersOfUser(isReceivable, pageRequest, userId);
        return orders.stream().map(m -> {

            List<DetailProductDTO> productDTOList = new ArrayList<>();
            for (DetailOrder d : m.getDetailOrders()) {
                ResponseEntity<DetailProductDTO> response = productServiceFeignClient.loadDetailProduct(d.getProductId());
                if (response.getStatusCode() == HttpStatus.OK) {
                    DetailProductDTO body = response.getBody();
                    if (body != null) {
                        productDTOList.add(DetailProductDTO.builder()
                                .productId(body.getProductId())
                                .productPrice(body.getProductPrice())
                                .productName(body.getProductName())
                                .productDescription(body.getProductDescription())
                                .category(body.getCategory())
                                .stockQuantity(body.getStockQuantity())
                                .imgUrl(body.getImgUrl())
                                .hashtags(body.getHashtags())
                                .build());
                    }
                }
            }
            return RealOrderListDTO.builder()
                    .orderUUID(m.getOrderUUID())
                    .orderId(m.getId())
                    .createdDate(m.getCreatedDate())
                    .products(productDTOList)
                    .build();

        }).toList();
    }

    @Transactional
    public DetailOrderDTO getDetailOrderOfUser(Long orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(RuntimeException::new);

        List<DetailOrder> detailOrderList = detailOrderRepository.searchAllByOrder(order);

        List<OrderProductDTO> orderProductDTOList = detailOrderList.stream().map(d -> {
            Long productId = d.getProductId();

            ResponseEntity<DetailProductDTO> response = productServiceFeignClient.loadDetailProduct(productId);

            if (response.getStatusCode() == HttpStatus.OK) {
                DetailProductDTO body = response.getBody();
                if (body != null) {
                    return OrderProductDTO.builder()
                            .productName(body.getProductName())
                            .productId(body.getProductId())
                            .price(body.getProductPrice())
                            .category(body.getCategory())
                            .hashtags(body.getHashtags())
                            .quantity(d.getQuantity())
                            .build();
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }).toList();

        return DetailOrderDTO.builder()
                .orderUUID(order.getOrderUUID())
                .orderState(order.getOrderState())
                .orderDate(order.getCreatedDate())
                .orderId(orderId)
                .requestOption(order.getRequestOption())
                .address(order.getAddress())
                .receiver(order.getReceiver())
                .userId(order.getUserId())
                .orderedProducts(orderProductDTOList)
                .build();
    }

    @Transactional
    public void changeOrderState(Long orderId, OrderState orderState) {

        Order order = orderRepository.findById(orderId).orElseThrow(RuntimeException::new);
        order.updateOrderState(orderState);
    }

    @Transactional
    public void cancelOrder(Long orderId, OrderState orderState) {
        Order order = orderRepository.findById(orderId).orElseThrow(RuntimeException::new);

        List<DetailOrder> detailOrders = detailOrderRepository.searchAllByOrder(order);

        for (DetailOrder detailOrder : detailOrders) {

            // 장바구니로 주문 했을 경우 되돌리기
            if (detailOrder.getDataState() != null && detailOrder.getDataState().contains("CART")) {
                Map<String, Boolean> state = new LinkedHashMap<>();
                state.put("isOrdered", true);

                String[] split = detailOrder.getDataState().split(" ");
                cartServiceFeignClient.updateDataState(Long.parseLong(split[1]), state);
            }

            // 각 상품의 재고 수량 되돌리기
            productServiceFeignClient.updateStockQuantity(
                    detailOrder.getProductId(),
                    UpdateStockQuantityDTO.builder()
                            .quantity(detailOrder.getQuantity())
                            .isAddable(true)
                            .build());
        }

        // 주문 상태 "취소 상태"로 변경
        changeOrderState(orderId, orderState);
    }
}
