package com.nolookcoding.orderservice.repository;

import com.nolookcoding.orderservice.domain.DetailOrder;
import com.nolookcoding.orderservice.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetailOrderRepository extends JpaRepository<DetailOrder, Long> {

    List<DetailOrder> searchAllByOrder(Order order);
}
