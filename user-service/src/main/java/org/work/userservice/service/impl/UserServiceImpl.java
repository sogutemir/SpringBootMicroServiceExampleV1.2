package org.work.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.work.userservice.exception.ResourceNotFoundException;
import org.work.userservice.model.dto.UserDto;
import org.work.userservice.model.entity.User;
import org.work.userservice.model.external.AccountServiceExternalAccountDto;
import org.work.userservice.model.mapper.UserMapper;
import org.work.userservice.repository.UserRepository;
import org.work.userservice.service.UserService;
import org.work.userservice.service.external.AccountServiceClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AccountServiceClient accountServiceClient;
    private final UserMapper userMapper = new UserMapper();

    @Override
    public UserDto createUser(UserDto userDto) {
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
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
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
}
