package com.storemanagement.controller;

import com.storemanagement.model.CreateProductDto;
import com.storemanagement.model.Product;
import com.storemanagement.model.UpdateProductDto;
import com.storemanagement.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @Operation(summary = "Add new product")
    public ResponseEntity<Product> addProduct (@RequestBody CreateProductDto createProductDto) {
        return ResponseEntity.ok(productService.addProduct(createProductDto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by id")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return productService.getProductById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product by id")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody UpdateProductDto updateProductDto) {
        return productService.getProductById(id).map(product -> ResponseEntity.ok(productService.updateProduct(id, updateProductDto)))
                .orElse(ResponseEntity.notFound().build());
    }
}
