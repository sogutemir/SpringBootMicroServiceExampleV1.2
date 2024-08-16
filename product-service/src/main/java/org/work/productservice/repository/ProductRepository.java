package org.work.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.work.productservice.model.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
