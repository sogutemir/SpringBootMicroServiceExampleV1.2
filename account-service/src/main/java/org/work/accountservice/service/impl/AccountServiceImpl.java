package org.work.accountservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.work.accountservice.exception.ResourceNotFoundException;
import org.work.accountservice.model.dto.AccountDto;
import org.work.accountservice.model.entity.Account;
import org.work.accountservice.model.mapper.AccountMapper;
import org.work.accountservice.repository.AccountRepository;
import org.work.accountservice.service.AccountService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper = new AccountMapper();

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = accountMapper.convertToEntity(accountDto);
        Account savedAccount = accountRepository.save(account);
        return accountMapper.convertToDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
        return accountMapper.convertToDto(account);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(accountMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto updateAccount(Long id, AccountDto accountDto) {
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
        Account updatedAccount = accountMapper.convertToEntity(accountDto);
        updatedAccount.setId(existingAccount.getId());
        Account savedAccount = accountRepository.save(updatedAccount);
        return accountMapper.convertToDto(savedAccount);
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
        accountRepository.delete(account);
    }
}
