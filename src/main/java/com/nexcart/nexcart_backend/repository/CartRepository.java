package com.nexcart.nexcart_backend.repository;

import com.nexcart.nexcart_backend.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
Cart findByUser_IdAndProduct_ProductId(Long userId, Long productId);
}
