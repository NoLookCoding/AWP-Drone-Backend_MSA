package com.nolookcoding.orderservice.repository;

import com.nolookcoding.orderservice.domain.DetailOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailOrderRepository extends JpaRepository<DetailOrder, Long> {
}
