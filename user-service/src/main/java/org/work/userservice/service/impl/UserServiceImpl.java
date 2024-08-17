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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AccountServiceClient accountServiceClient;
    private final ProductServiceClient productServiceClient;
    private final OrderServiceClient orderServiceClient;
    private final UserMapper userMapper = new UserMapper();
    private static final Logger log = Logger.getLogger(UserServiceImpl.class.getName());

    @Override
    public UserDto createUser(UserDto userDto) {
        try {
            if (userDto == null) {
                throw new InvalidInputException("User data cannot be null");
            }
            User user = userMapper.convertToEntity(userDto);
            User savedUser = userRepository.save(user);
            return userMapper.convertToDto(savedUser);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error creating user", e);
            throw e;
        }
    }

    @Override
    public UserDto getUserById(Long id) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
            return userMapper.convertToDto(user);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error fetching user by id: " + id, e);
            throw e;
        }
    }

    @Override
    public Double getTotalSpendingByUserId(Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
            List<OrderServiceExternalOrderDto> orders = orderServiceClient.getOrdersByUserId(userId);
            return orders.stream()
                    .mapToDouble(OrderServiceExternalOrderDto::getTotalPrice)
                    .sum();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error calculating total spending for userId: " + userId, e);
            throw e;
        }
    }

    @Override
    public List<UserDto> getAllUsers() {
        try {
            return userRepository.findAll().stream()
                    .map(userMapper::convertToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error fetching all users", e);
            throw e;
        }
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        try {
            if (userDto == null) {
                throw new InvalidInputException("User data cannot be null");
            }
            User existingUser = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
            User updatedUser = userMapper.convertToEntity(userDto);
            updatedUser.setId(existingUser.getId());
            User savedUser = userRepository.save(updatedUser);
            return userMapper.convertToDto(savedUser);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error updating user with id: " + id, e);
            throw e;
        }
    }

    @Override
    public void deleteUser(Long id) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
            userRepository.delete(user);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error deleting user with id: " + id, e);
            throw e;
        }
    }

    @Override
    public AccountServiceExternalAccountDto getAccountByUserId(Long accountId) {
        try {
            return accountServiceClient.getAccountById(accountId);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error fetching account for accountId: " + accountId, e);
            throw e;
        }
    }

    @Override
    public List<ProductServiceExternalProductDto> getProductsByUserId(Long userId) {
        try {
            return productServiceClient.getProductsByUserId(userId);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error fetching products for userId: " + userId, e);
            throw e;
        }
    }

    @Override
    public List<OrderServiceExternalOrderDto> getOrdersByUserId(Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
            return orderServiceClient.getOrdersByUserId(userId);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error fetching orders for userId: " + userId, e);
            throw e;
        }
    }

    @Override
    public List<OrderServiceExternalOrderDto> getTop5MostRecentOrders(Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
            List<OrderServiceExternalOrderDto> orders = orderServiceClient.getOrdersByUserId(userId);
            return orders.stream()
                    .sorted(Comparator.comparing(OrderServiceExternalOrderDto::getCreatedAt).reversed())
                    .limit(5)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error fetching top 5 most recent orders for userId: " + userId, e);
            throw e;
        }
    }
}