package com.sherm.pfinance.controllers;

import com.sherm.pfinance.models.Accounts;
import com.sherm.pfinance.services.AccountsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Accounts> updateAccount(@PathVariable Long id, @RequestBody Accounts accountDetails) {
        Optional<Accounts> optionalAccount = Optional.ofNullable(accountService.findById(id));

        if (optionalAccount.isPresent()) {
            Accounts account = optionalAccount.get();
            account.setName(accountDetails.getName());
            account.setBalance(accountDetails.getBalance());
            account.setCurrency(accountDetails.getCurrency());
	    account.setUser(accountDetails.getUser());
            Accounts updatedAccount = accountService.save(account);
            return ResponseEntity.ok(updatedAccount);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteById(id);
    }
}
