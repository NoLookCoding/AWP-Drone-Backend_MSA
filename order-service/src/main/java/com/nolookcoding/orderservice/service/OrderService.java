package com.nolookcoding.orderservice.service;

import com.nolookcoding.orderservice.domain.DetailOrder;
import com.nolookcoding.orderservice.domain.Order;
import com.nolookcoding.orderservice.domain.OrderState;
import com.nolookcoding.orderservice.dto.cart.CartListDTO;
import com.nolookcoding.orderservice.dto.order.CartOrderCreateDTO;
import com.nolookcoding.orderservice.dto.order.DirectOrderCreateDTO;
import com.nolookcoding.orderservice.repository.DetailOrderRepository;
import com.nolookcoding.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final DetailOrderRepository detailOrderRepository;

    @Transactional
    public Long createDirectOrder(DirectOrderCreateDTO directOrderCreateDTO) {
        Order order = Order.builder()
                .orderUUID(generateRandomUID())
                .address(directOrderCreateDTO.getAddress())
                .receiver(directOrderCreateDTO.getReceiver())
                .orderState(OrderState.PAID)
                .phoneNumber(directOrderCreateDTO.getPhoneNumber())
                .requestOption(directOrderCreateDTO.getRequestOption())
                .userId(directOrderCreateDTO.getUserId())
                .totalPrice(directOrderCreateDTO.getTotalPrice())
                .build();

        DetailOrder detailOrder = DetailOrder.builder()
                .quantity(directOrderCreateDTO.getQuantity())
                .productId(directOrderCreateDTO.getProductId())
                .order(order)
                .build();

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
                .totalPrice(cartOrderCreateDTO.getTotalPrice())
                .build();

        Long orderId = orderRepository.save(order).getId();


        for (CartListDTO c : cartListDTO) {
            detailOrderRepository.save(DetailOrder.builder()
                    .quantity(c.getQuantity())
                    .productId(c.getProductId())
                    .order(order)
                    .build());
        }

        return orderId;

    }

    private String generateRandomUID() {
        return UUID.randomUUID().toString();
    }
}
