package org.work.userservice.service;

import org.work.userservice.model.dto.UserDto;
import org.work.userservice.model.external.AccountServiceExternalAccountDto;
import org.work.userservice.model.external.OrderServiceExternalOrderDto;
import org.work.userservice.model.external.ProductServiceExternalProductDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserById(Long id);
    List<UserDto> getAllUsers();
    UserDto updateUser(Long id, UserDto userDto);
    void deleteUser(Long id);
    Double getTotalSpendingByUserId(Long userId);

    AccountServiceExternalAccountDto getAccountByUserId(Long accountId);

    ProductServiceExternalProductDto getProductById(Long productId);

    List<ProductServiceExternalProductDto> getProductsByUserId(Long userId);
    //OrderServiceExternalOrderDto
    List<OrderServiceExternalOrderDto> getTop5OrdersByTotalPrice(Long userId);
    List<OrderServiceExternalOrderDto> getOrdersByUserId(Long userId);
    List<OrderServiceExternalOrderDto> getTop5MostRecentOrders(Long userId);
    List<OrderServiceExternalOrderDto> getOrdersByUserIdAndStatus(Long userId, String status);

}