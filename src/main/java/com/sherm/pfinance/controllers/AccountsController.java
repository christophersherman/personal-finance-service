package com.sherm.pfinance.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sherm.pfinance.models.Accounts;
import com.sherm.pfinance.repositories.AccountsRepository;

@RestController
public class AccountsController {

    @Autowired
    private AccountsRepository accountsRepository;

    @GetMapping("/accounts")
    public List<Accounts> getAllAccounts() {
        return accountsRepository.findAll();
    }

    @PostMapping("/accounts")
    public Accounts createAccount(@RequestBody Accounts account) {
        return accountsRepository.save(account);
    }
}
