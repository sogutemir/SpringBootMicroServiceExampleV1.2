package org.work.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.work.orderservice.model.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
