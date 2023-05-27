package com.nolookcoding.cartservice.service;

import com.nolookcoding.cartservice.controller.ProductServiceFeignClient;
import com.nolookcoding.cartservice.controller.UserServiceFeignClient;
import com.nolookcoding.cartservice.domain.Cart;
import com.nolookcoding.cartservice.dto.CartListDTO;
import com.nolookcoding.cartservice.dto.CartRequestDTO;
import com.nolookcoding.cartservice.dto.DetailProductDTO;
import com.nolookcoding.cartservice.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductServiceFeignClient productServiceFeignClient;
    private final UserServiceFeignClient userServiceFeignClient;

    public Long createCart(String sessionValue, CartRequestDTO cartRequestDTO) {

        Long userId = null;
        if (sessionValue != null) {
            ResponseEntity<Long> chk = userServiceFeignClient.sessionCheck(sessionValue);
            if (chk.getStatusCode() == HttpStatus.OK) {
                if (chk.getBody() != null) {
                    userId = chk.getBody();
                }
            }
        } else
            return null;
        ResponseEntity<DetailProductDTO> response = productServiceFeignClient.loadDetailProduct(cartRequestDTO.getProductId());
        if (response.getStatusCode() == HttpStatus.OK) {
            if (response.getBody() != null) {
                if (response.getBody().getStockQuantity() >= cartRequestDTO.getQuantity()) {
                    Cart cart = Cart.builder()
                            .userId(userId)
                            .productId(cartRequestDTO.getProductId())
                            .quantity(cartRequestDTO.getQuantity())
                            .build();
                    return cartRepository.save(cart).getId();
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    public void updateCart(Long cartId, int quantity, boolean isAddable) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found")); // Replace with a custom exception in real code
        ResponseEntity<DetailProductDTO> response = productServiceFeignClient.loadDetailProduct(cart.getProductId());
        if (response.getStatusCode() == HttpStatus.OK) {
            if (response.getBody() != null) {
                cart.updateCartQuantity(quantity, isAddable, response.getBody().getStockQuantity());
            } else
                throw new RuntimeException("상품을 찾을 수 없습니다.");
        } else
            throw new RuntimeException("수량을 변경할 수 없습니다.");
    }

    public void deleteCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    public List<CartListDTO> getCartsByUserId(String sessionValue) {

        Long userId = null;
        ResponseEntity<Long> sessionCheck = userServiceFeignClient.sessionCheck(sessionValue);
        if (sessionCheck.getStatusCode() == HttpStatus.OK) {
            if (sessionCheck.getBody() != null) {
                userId = sessionCheck.getBody();
            }
        }
        List<Cart> carts = cartRepository.findAllByDataStateIsNullAndUserId(userId);
        return carts.stream().map(c -> {
            ResponseEntity<DetailProductDTO> response = productServiceFeignClient.loadDetailProduct(c.getProductId());

            if (response.getStatusCode() == HttpStatus.OK) {
                DetailProductDTO body = response.getBody();
                if (body != null) {
                    return CartListDTO.builder()
                            .price(body.getProductPrice())
                            .productName(body.getProductName())
                            .quantity(c.getQuantity())
                            .imgUrl(body.getImgUrl())
                            .category(body.getCategory())
                            .cartId(c.getId())
                            .productId(body.getProductId())
                            .build();
                }
                return null;
            }
            return null;
        }).toList();
    }

    public void updateDataState(Long cartId, boolean isOrdered) {

        Cart cart = cartRepository.findById(cartId).orElseThrow(RuntimeException::new);
        cart.updateDataState(isOrdered ? null : "ORDERED");
    }

}
