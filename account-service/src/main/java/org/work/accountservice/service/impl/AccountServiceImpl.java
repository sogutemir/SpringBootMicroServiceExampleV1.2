package org.work.accountservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.work.accountservice.exception.ResourceNotFoundException;
import org.work.accountservice.model.dto.AccountDto;
import org.work.accountservice.model.entity.Account;
import org.work.accountservice.model.external.UserServiceExternalUserDto;
import org.work.accountservice.model.mapper.AccountMapper;
import org.work.accountservice.repository.AccountRepository;
import org.work.accountservice.service.AccountService;
import org.work.accountservice.service.external.UserServiceClient;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper = new AccountMapper();
    private static final Logger log = Logger.getLogger(AccountServiceImpl.class.getName());
    private final UserServiceClient userServiceClient;

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        log.info("Creating account: " + accountDto);
        try {
            UserServiceExternalUserDto user = userServiceClient.getUserById(accountDto.getUserId());
            if (user == null) {
                log.warning("User not found with id: " + accountDto.getUserId());
                throw new ResourceNotFoundException("User not found with id: " + accountDto.getUserId());
            }
            Account account = accountMapper.convertToEntity(accountDto);
            Account savedAccount = accountRepository.save(account);

            if (savedAccount.getId() == null) {
                log.warning("Account saving failed.");
                throw new RuntimeException("Account saving failed.");
            }

            user.setAccountId(savedAccount.getId());
            UserServiceExternalUserDto updatedUser = userServiceClient.updateUserById(accountDto.getUserId(), user);

            log.info("Successfully updated user: " + updatedUser);
            log.info("Account created successfully with id: " + savedAccount.getId());
            return accountMapper.convertToDto(savedAccount);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error creating account", e);
            throw e;
        }
    }

    @Override
    public AccountDto getAccountById(Long id) {
        try {
            Account account = accountRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
            log.info("Account fetched successfully with id: " + id);
            return accountMapper.convertToDto(account);
        } catch (ResourceNotFoundException e) {
            log.log(Level.SEVERE, "Account not found with id: " + id, e);
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error fetching account with id: " + id, e);
            throw e;
        }
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        try {
            List<AccountDto> accounts = accountRepository.findAll().stream()
                    .map(accountMapper::convertToDto)
                    .collect(Collectors.toList());
            log.info("All accounts fetched successfully");
            return accounts;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error fetching all accounts", e);
            throw e;
        }
    }

    @Override
    public AccountDto updateAccount(Long id, AccountDto accountDto) {
        try {
            Account existingAccount = accountRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
            Account updatedAccount = accountMapper.convertToEntity(accountDto);
            updatedAccount.setId(existingAccount.getId());
            Account savedAccount = accountRepository.save(updatedAccount);
            log.info("Account updated successfully with id: " + id);
            return accountMapper.convertToDto(savedAccount);
        } catch (ResourceNotFoundException e) {
            log.log(Level.SEVERE, "Account not found with id: " + id, e);
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error updating account with id: " + id, e);
            throw e;
        }
    }

    @Override
    public void deleteAccount(Long id) {
        try {
            Account account = accountRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
            accountRepository.delete(account);
            log.info("Account deleted successfully with id: " + id);
        } catch (ResourceNotFoundException e) {
            log.log(Level.SEVERE, "Account not found with id: " + id, e);
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error deleting account with id: " + id, e);
            throw e;
        }
    }
}