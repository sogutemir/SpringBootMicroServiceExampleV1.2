package org.work.userservice.service;

import org.work.userservice.model.dto.UserDto;
import org.work.userservice.model.external.AccountServiceExternalAccountDto;
import org.work.userservice.model.external.OrderDto;
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

    List<ProductServiceExternalProductDto> getProductsByUserId(Long userId);
    //OrderServiceExternalOrderDto
    List<OrderDto> getOrdersByUserId(Long userId);
    List<OrderDto> getTop5MostRecentOrders(Long userId);

}