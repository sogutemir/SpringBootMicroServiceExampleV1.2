package org.work.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.work.orderservice.model.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.userId = :userId")
    Optional<Order> findByUserId(@Param("userId") Long userId);

    @Query("SELECT o FROM Order o WHERE o.productId = :productId")
    Optional<Order> findByProductId(@Param("productId") Long productId);

    @Query("SELECT o FROM Order o WHERE o.totalPrice = :totalPrice")
    List<Order> findByTotalPrice(@Param("totalPrice") Double totalPrice);

    @Query("SELECT o FROM Order o WHERE o.totalPrice >= :minPrice AND o.totalPrice <= :maxPrice")
    List<Order> findByTotalPriceRange(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);

    @Query("SELECT o FROM Order o WHERE o.userId = :userId AND o.orderStatus = :status")
    List<Order> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") String status);

    @Query("SELECT o FROM Order o WHERE o.userId = :userId ORDER BY o.orderDate DESC")
    List<Order> findTop5ByUserIdOrderByOrderDateDesc(@Param("userId") Long userId);

}