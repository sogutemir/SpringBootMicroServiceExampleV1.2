package org.work.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.work.productservice.model.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.userId = :userId")
    List<Product> findByUserId(@Param("userId") Long userId);

    @Query("SELECT p FROM Product p WHERE p.userId = :userId" +
            " AND (:minPrice IS NULL OR p.price >= :minPrice)" +
            " AND (:maxPrice IS NULL OR p.price <= :maxPrice)")
    List<Product> findByUserIdAndPriceRange(@Param("userId") Long userId,
                                            @Param("minPrice") Double minPrice,
                                            @Param("maxPrice") Double maxPrice);

    @Query("SELECT p FROM Product p WHERE p.orderId = :orderId")
    Optional<Product> findByOrderId(@Param("orderId") Long orderId);


    @Query("SELECT p FROM Product p WHERE p.notificationIds = :notificationIds")
    Optional<Product> findByNotificationId(@Param("notificationIds") Long notificationIds);

}