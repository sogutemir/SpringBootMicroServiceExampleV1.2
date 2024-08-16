package org.work.productservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.work.productservice.exception.ResourceNotFoundException;
import org.work.productservice.model.dto.ProductDto;
import org.work.productservice.model.entity.Product;
import org.work.productservice.model.mapper.ProductMapper;
import org.work.productservice.repository.ProductRepository;
import org.work.productservice.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper = new ProductMapper();

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = productMapper.convertToEntity(productDto);
        Product savedProduct = productRepository.save(product);
        return productMapper.convertToDto(savedProduct);
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return productMapper.convertToDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        Product updatedProduct = productMapper.convertToEntity(productDto);
        updatedProduct.setId(existingProduct.getId());
        Product savedProduct = productRepository.save(updatedProduct);
        return productMapper.convertToDto(savedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }
}
