package com.sherm.pfinance.services;

import com.sherm.pfinance.models.Accounts;
import com.sherm.pfinance.repositories.AccountsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountsService {

    private final AccountsRepository accountsRepository;

    public AccountsService(AccountsRepository accountRepository) {
        this.accountsRepository = accountRepository;
    }

    public List<Accounts> findAll() {
        return accountsRepository.findAll();
    }

    public Accounts findById(String id) {
        return accountsRepository.findById(id).orElse(null);
    }

    public Accounts save(Accounts account) {
        return accountsRepository.save(account);
    }

    public void deleteById(String id) {
        accountsRepository.deleteById(id);
    }
}
