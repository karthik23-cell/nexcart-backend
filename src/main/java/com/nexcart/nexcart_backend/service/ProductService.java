package com.nexcart.nexcart_backend.service;

import com.nexcart.nexcart_backend.entity.Product;
import com.nexcart.nexcart_backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public void addProduct(Product product){
        productRepository.save(product);
    }
}
