package org.work.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.work.productservice.model.dto.ProductDto;
import org.work.productservice.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto createdProduct = productService.createProduct(productDto);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto productDto = productService.getProductById(id);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        ProductDto updatedProduct = productService.updateProduct(id, productDto);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProductDto>> getProductsByUserId(@PathVariable Long userId) {
        List<ProductDto> products = productService.getProductsByUserId(userId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/price-range")
    public ResponseEntity<List<ProductDto>> getProductsByUserIdAndPriceRange(
            @PathVariable Long userId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        List<ProductDto> products = productService.getProductsByUserIdAndPriceRange(userId, minPrice, maxPrice);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ProductDto> getProductByOrderId(@PathVariable Long orderId) {
        ProductDto product = productService.getProductByOrderId(orderId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/notification/{notificationId}")
    public ResponseEntity<List<ProductDto>> getProductsByNotificationId(@PathVariable Long notificationId) {
        List<ProductDto> products = productService.getProductsByNotificationId(notificationId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}