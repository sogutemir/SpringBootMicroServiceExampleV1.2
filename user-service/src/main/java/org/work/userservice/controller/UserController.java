package org.work.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.work.userservice.model.dto.UserDto;
import org.work.userservice.model.external.OrderServiceExternalOrderDto;
import org.work.userservice.model.external.ProductServiceExternalProductDto;
import org.work.userservice.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto userDto = userService.getUserById(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/{userId}/total-spending")
    public ResponseEntity<Double> getTotalSpendingByUserId(@PathVariable Long userId) {
        Double totalSpending = userService.getTotalSpendingByUserId(userId);
        return new ResponseEntity<>(totalSpending, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUser(id, userDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{userId}/products")
    public ResponseEntity<List<ProductServiceExternalProductDto>> getProductsByUserId(@PathVariable Long userId) {
        List<ProductServiceExternalProductDto> products = userService.getProductsByUserId(userId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductServiceExternalProductDto> getProductById(@PathVariable Long productId) {
        ProductServiceExternalProductDto product = userService.getProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/orders/{orderId}/products")
    public ResponseEntity<List<ProductServiceExternalProductDto>> getProductsByOrderId(@PathVariable Long orderId) {
        List<ProductServiceExternalProductDto> products = userService.getProductsByOrderId(orderId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/notifications/{notificationId}/products")
    public ResponseEntity<List<ProductServiceExternalProductDto>> getProductsByNotificationId(@PathVariable Long notificationId) {
        List<ProductServiceExternalProductDto> products = userService.getProductsByNotificationId(notificationId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{userId}/products/price-range")
    public ResponseEntity<List<ProductServiceExternalProductDto>> getProductsByUserIdAndPriceRange(
            @PathVariable Long userId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        List<ProductServiceExternalProductDto> products = userService.getProductsByUserIdAndPriceRange(userId, minPrice, maxPrice);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    /*
    * order-service methods
     */
    @GetMapping("/{userId}/top-orders")
    public ResponseEntity<List<OrderServiceExternalOrderDto>> getTop5OrdersByTotalPrice(@PathVariable Long userId) {
        List<OrderServiceExternalOrderDto> orders = userService.getTop5OrdersByTotalPrice(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<OrderServiceExternalOrderDto>> getOrdersByUserId(@PathVariable Long userId) {
        List<OrderServiceExternalOrderDto> orders = userService.getOrdersByUserId(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{userId}/orders/recent")
    public ResponseEntity<List<OrderServiceExternalOrderDto>> getTop5MostRecentOrders(@PathVariable Long userId) {
        List<OrderServiceExternalOrderDto> orders = userService.getTop5MostRecentOrders(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{userId}/orders/status/{status}")
    public ResponseEntity<List<OrderServiceExternalOrderDto>> getOrdersByUserIdAndStatus(@PathVariable Long userId, @PathVariable String status) {
        List<OrderServiceExternalOrderDto> orders = userService.getOrdersByUserIdAndStatus(userId, status);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{userId}/most-frequent-product")
    public ResponseEntity<ProductServiceExternalProductDto> getMostFrequentlyOrderedProductByUserId(@PathVariable Long userId) {
        ProductServiceExternalProductDto product = userService.getMostFrequentlyOrderedProductByUserId(userId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
