package org.work.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.work.orderservice.model.dto.OrderDto;
import org.work.orderservice.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        OrderDto createdOrder = orderService.createOrder(orderDto);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        OrderDto orderDto = orderService.getOrderById(id);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @RequestBody OrderDto orderDto) {
        OrderDto updatedOrder = orderService.updateOrder(id, orderDto);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<OrderDto> getOrderByUserId(@PathVariable Long userId) {
        OrderDto order = orderService.getOrderByUserId(userId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<OrderDto> getOrderByProductId(@PathVariable Long productId) {
        OrderDto order = orderService.getOrderByProductId(productId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/total-price")
    public ResponseEntity<List<OrderDto>> getOrdersByTotalPrice(@RequestParam Double totalPrice) {
        List<OrderDto> orders = orderService.getOrdersByTotalPrice(totalPrice);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/total-price-range")
    public ResponseEntity<List<OrderDto>> getOrdersByTotalPriceRange(@RequestParam Double minPrice, @RequestParam Double maxPrice) {
        List<OrderDto> orders = orderService.getOrdersByTotalPriceRange(minPrice, maxPrice);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<OrderDto>> getOrdersByUserIdAndStatus(@PathVariable Long userId, @PathVariable String status) {
        List<OrderDto> orders = orderService.getOrdersByUserIdAndStatus(userId, status);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/recent")
    public ResponseEntity<List<OrderDto>> getRecentOrdersByUserId(@PathVariable Long userId) {
        List<OrderDto> recentOrders = orderService.getRecentOrdersByUserId(userId);
        return new ResponseEntity<>(recentOrders, HttpStatus.OK);
    }



}