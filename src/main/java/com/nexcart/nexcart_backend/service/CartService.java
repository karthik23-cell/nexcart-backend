package com.nexcart.nexcart_backend.service;

import com.nexcart.nexcart_backend.entity.Cart;
import com.nexcart.nexcart_backend.entity.Product;
import com.nexcart.nexcart_backend.entity.User;
import com.nexcart.nexcart_backend.repository.CartRepository;
import com.nexcart.nexcart_backend.repository.ProductRepository;
import com.nexcart.nexcart_backend.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepo userRepo;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, UserRepo userRepo, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepo = userRepo;
        this.productRepository = productRepository;
    }
    public void addToCart(Long userId, Long productId) {
        Cart cart = cartRepository.findByUser_IdAndProduct_ProductId(userId, productId);
        if (cart != null) {
            cart.setQuantity(cart.getQuantity() + 1);
            cartRepository.save(cart);
        }
        else {
            Optional<User> user = userRepo.findById(userId);
            Optional<Product> product = productRepository.findById(productId);
            if (user.isPresent() && product.isPresent()) {
                User actualUser = user.get();
                Product actualProduct = product.get();
                Cart newCart = new Cart();
                newCart.setUser(actualUser);
                newCart.setProduct(actualProduct);
                newCart.setQuantity(1);
                cartRepository.save(newCart);
            }
        }
    }
}
