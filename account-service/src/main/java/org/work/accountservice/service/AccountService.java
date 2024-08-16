package org.work.accountservice.service;

import org.work.accountservice.model.dto.AccountDto;

import java.util.List;

public interface AccountService {
    AccountDto createAccount(AccountDto accountDto);
    AccountDto getAccountById(Long id);
    List<AccountDto> getAllAccounts();
    AccountDto updateAccount(Long id, AccountDto accountDto);
    void deleteAccount(Long id);
}
