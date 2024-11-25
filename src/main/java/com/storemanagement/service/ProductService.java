package com.storemanagement.service;

import com.storemanagement.exception.ProductNotFoundException;
import com.storemanagement.model.CreateProductDto;
import com.storemanagement.model.Product;
import com.storemanagement.model.ProductDto;
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

    public ProductDto addProduct (CreateProductDto createProductDto) {
        Product product = new Product();
        product.setName(createProductDto.name());
        product.setDescription(createProductDto.description());
        product.setPrice(createProductDto.price());

        Product savedProduct = productRepository.save(product);
        return mapToDto(savedProduct);
    }

    public Optional<ProductDto> getProductById(Long id) {
        return productRepository.findByIdAndDeletedFalse(id)
                .map(this::mapToDto);
    }

    public ProductDto updateProduct(Long id, UpdateProductDto updateProductDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        product.setName(updateProductDto.name());
        product.setDescription(updateProductDto.description());
        product.setPrice(updateProductDto.price());
        Product updatedProduct = productRepository.save(product);
        return mapToDto(updatedProduct);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        product.setDeleted(true);
        productRepository.save(product);
    }

    private ProductDto mapToDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }
}
