package org.work.productservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.work.productservice.exception.ResourceNotFoundException;
import org.work.productservice.model.dto.ProductDto;
import org.work.productservice.model.entity.Product;
import org.work.productservice.model.external.UserServiceExternalUserDto;
import org.work.productservice.model.mapper.ProductMapper;
import org.work.productservice.repository.ProductRepository;
import org.work.productservice.service.ProductService;
import org.work.productservice.service.external.UserServiceClient;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper = new ProductMapper();
    private final UserServiceClient userServiceClient;
    private static final Logger log = Logger.getLogger(ProductServiceImpl.class.getName());

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        log.info("Creating product: " + productDto);
        try {
            UserServiceExternalUserDto user = userServiceClient.getUsersById(productDto.getUserId());
            if (user == null) {
                log.warning("User not found with id: " + productDto.getUserId());
                throw new ResourceNotFoundException("User not found with id: " + productDto.getUserId());
            }

            Product product = productMapper.convertToEntity(productDto);
            Product savedProduct = productRepository.save(product);

            user.getProductIds().add(savedProduct.getId());
            UserServiceExternalUserDto updatedUser = userServiceClient.updateUserById(productDto.getUserId(), user);

            log.info("Successfully updated user: " + updatedUser);
            log.info("Successfully created product: " + savedProduct);

            return productMapper.convertToDto(savedProduct);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error creating product: " + productDto, e);
            throw e;
        }
    }



    @Override
    public ProductDto getProductById(Long id) {
        log.info("Fetching product by id: " + id);
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
            log.info("Successfully fetched product: " + product);
            return productMapper.convertToDto(product);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error fetching product by id: " + id, e);
            throw e;
        }
    }

    @Override
    public List<ProductDto> getAllProducts() {
        log.info("Fetching all products");
        try {
            List<ProductDto> products = productRepository.findAll().stream()
                    .map(productMapper::convertToDto)
                    .collect(Collectors.toList());
            log.info("Successfully fetched all products");
            return products;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error fetching all products", e);
            throw e;
        }
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        log.info("Updating product with id: " + id);
        try {
            Product existingProduct = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
            Product updatedProduct = productMapper.convertToEntity(productDto);
            updatedProduct.setId(existingProduct.getId());
            Product savedProduct = productRepository.save(updatedProduct);
            log.info("Successfully updated product: " + savedProduct);
            return productMapper.convertToDto(savedProduct);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error updating product with id: " + id, e);
            throw e;
        }
    }

    @Override
    public void deleteProduct(Long id) {
        log.info("Deleting product with id: " + id);
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
            productRepository.delete(product);
            log.info("Successfully deleted product with id: " + id);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error deleting product with id: " + id, e);
            throw e;
        }
    }

    @Override
    public List<ProductDto> getProductsByUserId(Long userId) {
        log.info("Fetching products by userId: " + userId);
        try {
            List<ProductDto> products = productRepository.findByUserId(userId).stream()
                    .map(productMapper::convertToDto)
                    .collect(Collectors.toList());
            log.info("Successfully fetched products by userId: " + userId);
            return products;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error fetching products by userId: " + userId, e);
            throw e;
        }
    }

    @Override
    public List<ProductDto> getProductsByCategory(String category) {
        log.info("Fetching products by category: " + category);
        try {
            List<ProductDto> products = productRepository.findByCategory(category).stream()
                    .map(productMapper::convertToDto)
                    .collect(Collectors.toList());
            log.info("Successfully fetched products by category: " + category);
            return products;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error fetching products by category: " + category, e);
            throw e;
        }
    }

    @Override
    public List<ProductDto> getProductsByPriceRange(Double minPrice, Double maxPrice) {
        log.info("Fetching products by price range: " + minPrice + " - " + maxPrice);
        try {
            List<ProductDto> products = productRepository.findByPriceBetween(minPrice, maxPrice).stream()
                    .map(productMapper::convertToDto)
                    .collect(Collectors.toList());
            log.info("Successfully fetched products by price range: " + minPrice + " - " + maxPrice);
            return products;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error fetching products by price range: " + minPrice + " - " + maxPrice, e);
            throw e;
        }
    }
}