package com.storemanagement.service;

import com.storemanagement.model.Product;
import com.storemanagement.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct (Product product) {
        return productRepository.save(product);
    }
}