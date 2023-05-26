package com.nolookcoding.orderservice.repository;

import com.nolookcoding.orderservice.domain.Order;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderRepositoryCustom {

    List<Order> searchAllOrdersOfUser(Boolean isReceivable, Pageable pageable, Long userId);
}
