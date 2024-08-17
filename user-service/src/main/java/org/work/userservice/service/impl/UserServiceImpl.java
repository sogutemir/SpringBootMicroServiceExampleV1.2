package org.work.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.work.userservice.exception.ResourceNotFoundException;
import org.work.userservice.exception.InvalidInputException;
import org.work.userservice.model.dto.UserDto;
import org.work.userservice.model.entity.User;
import org.work.userservice.model.external.AccountServiceExternalAccountDto;
import org.work.userservice.model.external.OrderServiceExternalOrderDto;
import org.work.userservice.model.external.ProductServiceExternalProductDto;
import org.work.userservice.model.mapper.UserMapper;
import org.work.userservice.repository.UserRepository;
import org.work.userservice.service.UserService;
import org.work.userservice.service.external.AccountServiceClient;
import org.work.userservice.service.external.OrderServiceClient;
import org.work.userservice.service.external.ProductServiceClient;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AccountServiceClient accountServiceClient;
    private final ProductServiceClient productServiceClient;
    private final OrderServiceClient orderServiceClient;
    private final UserMapper userMapper = new UserMapper();

    @Override
    public UserDto createUser(UserDto userDto) {
        if (userDto == null) {
            throw new InvalidInputException("User data cannot be null");
        }
        User user = userMapper.convertToEntity(userDto);
        User savedUser = userRepository.save(user);
        return userMapper.convertToDto(savedUser);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userMapper.convertToDto(user);
    }


    @Override
    public Double getTotalSpendingByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        List<OrderServiceExternalOrderDto> orders = orderServiceClient.getOrdersByUserId(userId);
        return orders.stream()
                .mapToDouble(OrderServiceExternalOrderDto::getTotalPrice)
                .sum();
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        if (userDto == null) {
            throw new InvalidInputException("User data cannot be null");
        }
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        User updatedUser = userMapper.convertToEntity(userDto);
        updatedUser.setId(existingUser.getId());
        User savedUser = userRepository.save(updatedUser);
        return userMapper.convertToDto(savedUser);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }

    @Override
    public AccountServiceExternalAccountDto getAccountByUserId(Long accountId) {
        return accountServiceClient.getAccountById(accountId);
    }

    @Override
    public ProductServiceExternalProductDto getProductById(Long productId) {
        return productServiceClient.getProductById(productId);
    }

    @Override
    public List<ProductServiceExternalProductDto> getProductsByUserId(Long userId) {
        return productServiceClient.getProductsByUserId(userId);
    }

    @Override
    public List<ProductServiceExternalProductDto> getProductsByOrderId(Long orderId) {
        return productServiceClient.getProductsByOrderId(orderId);
    }

    @Override
    public List<ProductServiceExternalProductDto> getProductsByNotificationId(Long notificationId) {
        return productServiceClient.getProductsByNotificationId(notificationId);
    }

    @Override
    public List<ProductServiceExternalProductDto> getProductsByUserIdAndPriceRange(Long userId, Double minPrice, Double maxPrice) {
        return productServiceClient.getProductsByUserIdAndPriceRange(userId, minPrice, maxPrice);
    }


    @Override
    public List<OrderServiceExternalOrderDto> getTop5OrdersByTotalPrice(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        List<Long> orderIds = user.getOrderIds();
        List<OrderServiceExternalOrderDto> orders = orderServiceClient.getOrdersByIds(orderIds);

        return orders.stream()
                .sorted(Comparator.comparingDouble(OrderServiceExternalOrderDto::getTotalPrice).reversed())
                .limit(5)
                .collect(Collectors.toList());

    }

    @Override
    public List<OrderServiceExternalOrderDto> getOrdersByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return orderServiceClient.getOrdersByUserId(userId);
    }

    @Override
    public List<OrderServiceExternalOrderDto> getTop5MostRecentOrders(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        List<OrderServiceExternalOrderDto> orders = orderServiceClient.getOrdersByUserId(userId);
        return orders.stream()
                .sorted(Comparator.comparing(OrderServiceExternalOrderDto::getCreatedAt).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderServiceExternalOrderDto> getOrdersByUserIdAndStatus(Long userId, String status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return orderServiceClient.getOrdersByUserIdAndStatus(userId, status);
    }

    @Override
    public ProductServiceExternalProductDto getMostFrequentlyOrderedProductByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        List<OrderServiceExternalOrderDto> orders = orderServiceClient.getOrdersByUserId(userId);
        Map<Long, Long> productFrequency = orders.stream()
                .collect(Collectors.groupingBy(OrderServiceExternalOrderDto::getProductId, Collectors.counting()));
        Long mostFrequentProductId = productFrequency.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(() -> new ResourceNotFoundException("No products found for user with id: " + userId))
                .getKey();
        return productServiceClient.getProductById(mostFrequentProductId);
    }


}