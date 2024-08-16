package org.work.productservice.model.mapper;

import org.springframework.beans.BeanUtils;
import org.work.productservice.model.dto.ProductDto;
import org.work.productservice.model.entity.Product;

import java.util.Objects;

public class ProductMapper extends BaseMapper<Product, ProductDto> {

    @Override
    public Product convertToEntity(ProductDto dto, Object... args) {

        Product product = new Product();
        if(!Objects.isNull(dto)){
            BeanUtils.copyProperties(dto, product);
        }
        return product;
    }

    @Override
    public ProductDto convertToDto(Product entity, Object... args) {

        ProductDto productDto = new ProductDto();
        if(!Objects.isNull(entity)) {
            BeanUtils.copyProperties(entity, productDto);
        }
        return productDto;    }

}