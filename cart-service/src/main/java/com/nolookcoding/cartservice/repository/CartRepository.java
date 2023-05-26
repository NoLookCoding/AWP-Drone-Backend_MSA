package com.nolookcoding.cartservice.repository;

import com.nolookcoding.cartservice.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByDataStateIsNullAndUserId(Long userId);
}
