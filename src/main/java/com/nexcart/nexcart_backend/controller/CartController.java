package com.nexcart.nexcart_backend.controller;

import com.nexcart.nexcart_backend.service.CartService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    @PostMapping("/add")
    public String addToCart(@RequestParam Long userId, @RequestParam Long productId) {
        cartService.addToCart(userId, productId);
        return "Product added to cart successfully";
    }
    }
