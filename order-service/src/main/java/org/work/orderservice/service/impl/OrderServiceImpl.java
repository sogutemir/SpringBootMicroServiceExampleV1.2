package org.work.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.work.orderservice.exception.ResourceNotFoundException;
import org.work.orderservice.model.dto.OrderDto;
import org.work.orderservice.model.entity.Order;
import org.work.orderservice.model.mapper.OrderMapper;
import org.work.orderservice.repository.OrderRepository;
import org.work.orderservice.service.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper = new OrderMapper();

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        Order order = orderMapper.convertToEntity(orderDto);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.convertToDto(savedOrder);
    }

    @Override
    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return orderMapper.convertToDto(order);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto updateOrder(Long id, OrderDto orderDto) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        Order updatedOrder = orderMapper.convertToEntity(orderDto);
        updatedOrder.setId(existingOrder.getId());
        Order savedOrder = orderRepository.save(updatedOrder);
        return orderMapper.convertToDto(savedOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        orderRepository.delete(order);
    }
}