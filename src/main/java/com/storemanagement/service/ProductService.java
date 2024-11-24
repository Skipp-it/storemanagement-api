package com.storemanagement.service;

import com.storemanagement.model.Product;
import com.storemanagement.model.UpdateProductDto;
import com.storemanagement.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct (Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product updateProduct(Long id, UpdateProductDto updateProductDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        product.setName(updateProductDto.name());
        product.setDescription(updateProductDto.description());
        product.setPrice(updateProductDto.price());
        return productRepository.save(product);
    }
}
