package org.work.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.work.orderservice.exception.ResourceNotFoundException;
import org.work.orderservice.model.dto.OrderDto;
import org.work.orderservice.model.entity.Order;
import org.work.orderservice.model.external.UserServiceExternalUserDto;
import org.work.orderservice.model.mapper.OrderMapper;
import org.work.orderservice.repository.OrderRepository;
import org.work.orderservice.service.OrderService;
import org.work.orderservice.service.external.UserServiceClient;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper = new OrderMapper();
    private static final Logger log = Logger.getLogger(OrderServiceImpl.class.getName());
    private final UserServiceClient userServiceClient;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        log.info("Creating order: " + orderDto);
        try {
            UserServiceExternalUserDto user = userServiceClient.getUserById(orderDto.getUserId());
            if (user == null) {
                log.warning("User not found with id: " + orderDto.getUserId());
                throw new ResourceNotFoundException("User not found with id: " + orderDto.getUserId());
            }
            Order order = orderMapper.convertToEntity(orderDto);
            Order savedOrder = orderRepository.save(order);

            if (savedOrder.getId() == null) {
                log.warning("Order saving failed.");
                throw new RuntimeException("Order saving failed.");
            }

            user.getOrderIds().add(savedOrder.getId());
            UserServiceExternalUserDto updatedUser = userServiceClient.updateUserById(orderDto.getUserId(), user);

            log.info("Successfully updated user: " + updatedUser);
            log.info("Order created successfully with id: " + savedOrder.getId());
            kafkaTemplate.send("ORDER_TOPIC", savedOrder.getId().toString());

            return orderMapper.convertToDto(savedOrder);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error creating order", e);
            throw e;
        }
    }

    @Override
    public OrderDto getOrderById(Long id) {
        try {
            Order order = orderRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
            log.info("Order fetched successfully with id: " + id);
            return orderMapper.convertToDto(order);
        } catch (ResourceNotFoundException e) {
            log.log(Level.SEVERE, "Order not found with id: " + id, e);
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error fetching order with id: " + id, e);
            throw e;
        }
    }

    @Override
    public List<OrderDto> getAllOrders() {
        try {
            List<OrderDto> orders = orderRepository.findAll().stream()
                    .map(orderMapper::convertToDto)
                    .collect(Collectors.toList());
            log.info("All orders fetched successfully");
            return orders;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error fetching all orders", e);
            throw e;
        }
    }

    @Override
    public OrderDto updateOrder(Long id, OrderDto orderDto) {
        try {
            Order existingOrder = orderRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
            Order updatedOrder = orderMapper.convertToEntity(orderDto);
            updatedOrder.setId(existingOrder.getId());
            Order savedOrder = orderRepository.save(updatedOrder);
            log.info("Order updated successfully with id: " + id);
            return orderMapper.convertToDto(savedOrder);
        } catch (ResourceNotFoundException e) {
            log.log(Level.SEVERE, "Order not found with id: " + id, e);
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error updating order with id: " + id, e);
            throw e;
        }
    }

    @Override
    public void deleteOrder(Long id) {
        try {
            Order order = orderRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
            orderRepository.delete(order);
            log.info("Order deleted successfully with id: " + id);
        } catch (ResourceNotFoundException e) {
            log.log(Level.SEVERE, "Order not found with id: " + id, e);
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error deleting order with id: " + id, e);
            throw e;
        }
    }

    @Override
    public List<OrderDto> getOrdersByUserId(Long userId) {
        try {
            List<OrderDto> orders = orderRepository.findByUserId(userId).stream()
                    .map(orderMapper::convertToDto)
                    .collect(Collectors.toList());
            log.info("Orders fetched successfully for userId: " + userId);
            return orders;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error fetching orders for userId: " + userId, e);
            throw e;
        }
    }

    @Override
    public List<OrderDto> getOrdersByProductId(Long productId) {
        try {
            List<OrderDto> orders = orderRepository.findByProductId(productId).stream()
                    .map(orderMapper::convertToDto)
                    .collect(Collectors.toList());
            log.info("Orders fetched successfully for productId: " + productId);
            return orders;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error fetching orders for productId: " + productId, e);
            throw e;
        }
    }

    @Override
    public List<OrderDto> getOrdersByTotalPrice(Double totalPrice) {
        try {
            List<OrderDto> orders = orderRepository.findByTotalPrice(totalPrice).stream()
                    .map(orderMapper::convertToDto)
                    .collect(Collectors.toList());
            log.info("Orders fetched successfully for totalPrice: " + totalPrice);
            return orders;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error fetching orders for totalPrice: " + totalPrice, e);
            throw e;
        }
    }

    @Override
    public List<OrderDto> getOrdersByTotalPriceRange(Double minPrice, Double maxPrice) {
        try {
            List<OrderDto> orders = orderRepository.findByTotalPriceRange(minPrice, maxPrice).stream()
                    .map(orderMapper::convertToDto)
                    .collect(Collectors.toList());
            log.info("Orders fetched successfully for price range: " + minPrice + " - " + maxPrice);
            return orders;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error fetching orders for price range: " + minPrice + " - " + maxPrice, e);
            throw e;
        }
    }

    @Override
    public List<OrderDto> getOrdersByUserIdAndStatus(Long userId, String status) {
        try {
            List<Order> orders = orderRepository.findByUserIdAndStatus(userId, status);
            if (orders.isEmpty()) {
                throw new ResourceNotFoundException("No orders found for user with id: " + userId + " and status: " + status);
            }
            log.info("Orders fetched successfully for userId: " + userId + " and status: " + status);
            return orders.stream()
                    .map(orderMapper::convertToDto)
                    .collect(Collectors.toList());
        } catch (ResourceNotFoundException e) {
            log.log(Level.SEVERE, "No orders found for user with id: " + userId + " and status: " + status, e);
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error fetching orders for userId: " + userId + " and status: " + status, e);
            throw e;
        }
    }

    @Override
    public List<OrderDto> getRecentOrdersByUserId(Long userId) {
        try {
            List<OrderDto> orders = orderRepository.findTop5ByUserIdOrderByOrderDateDesc(userId).stream()
                    .map(orderMapper::convertToDto)
                    .collect(Collectors.toList());
            log.info("Recent orders fetched successfully for userId: " + userId);
            return orders;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error fetching recent orders for userId: " + userId, e);
            throw e;
        }
    }
}