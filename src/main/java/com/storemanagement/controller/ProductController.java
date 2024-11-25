package com.storemanagement.controller;

import com.storemanagement.model.CreateProductDto;
import com.storemanagement.model.ProductDto;
import com.storemanagement.model.UpdateProductDto;
import com.storemanagement.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Add new product")
    public ResponseEntity<ProductDto> addProduct (@RequestBody CreateProductDto createProductDto) {
        logger.info("Request to add new product: {}", createProductDto.name());
        ProductDto productDto = productService.addProduct(createProductDto);
        logger.debug("Product added with ID: {}", productDto.id());
        return ResponseEntity.ok(productDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by id")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        logger.info("Request to get product with ID: {}", id);
        return productService.getProductById(id)
                .map(productDto -> {
                    logger.debug("Product found: {}", productDto);
                    return ResponseEntity.ok(productDto);
                })
                .orElseGet(() -> {
                    logger.warn("Product with ID {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update product by id")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody UpdateProductDto updateProductDto) {
        logger.info("Request to update product with ID: {}", id);
        return productService.getProductById(id)
                .map(existingProduct -> {
                    ProductDto updatedProduct = productService.updateProduct(id, updateProductDto);
                    logger.debug("Product updated: {}", updatedProduct);
                    return ResponseEntity.ok(updatedProduct);
                })
                .orElseGet(() -> {
                    logger.warn("Product with ID {} not found for update", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product by id")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        logger.info("Request to delete product with ID: {}", id);
        try {
            productService.deleteProduct(id);
            logger.debug("Product with ID {} deleted", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting product with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }
}
