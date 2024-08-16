package org.work.userservice.service;

import org.work.userservice.model.dto.UserDto;
import org.work.userservice.model.external.AccountServiceExternalAccountDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserById(Long id);
    List<UserDto> getAllUsers();
    UserDto updateUser(Long id, UserDto userDto);
    void deleteUser(Long id);
    AccountServiceExternalAccountDto getAccountByUserId(Long accountId);
}