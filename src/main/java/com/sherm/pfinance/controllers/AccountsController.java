package com.sherm.pfinance.controllers;

import com.sherm.pfinance.models.Accounts;
import com.sherm.pfinance.services.AccountsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountsController {

    private final AccountsService accountService;

    public AccountsController(AccountsService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<Accounts> getAllAccounts() {
        return accountService.findAll();
    }

    @GetMapping("/{id}")
    public Accounts getAccountById(@PathVariable Long id) {
        return accountService.findById(id);
    }

    @PostMapping
    public Accounts createAccount(@RequestBody Accounts account) {
        return accountService.save(account);
    }

    @PutMapping("/{id}")
    public Accounts updateAccount(@PathVariable Long id, @RequestBody Accounts account) {
        account.setId(id);
        return accountService.save(account);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteById(id);
    }
}
