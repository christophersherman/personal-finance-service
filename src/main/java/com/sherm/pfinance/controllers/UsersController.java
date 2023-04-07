package com.sherm.pfinance.controllers;

import com.sherm.pfinance.models.Users;
import com.sherm.pfinance.services.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UsersController {

   private final UsersService usersService;

   public UsersController(UsersService usersService) {
	   this.usersService = usersService;
   }

   @GetMapping
   public List<Users> getAllAccounts() {
       return usersService.findAll();
   }

   @GetMapping("/{id}")
   public Users getUserById(@PathVariable Long id) {
       return usersService.findById(id);
   }

   @PostMapping
   public Users createUser(@RequestBody Users user) {
       return usersService.save(user);
   }

   @PutMapping("/{id}")
   public ResponseEntity<Users> updateUser(@PathVariable Long id, @RequestBody Users userDetails) {
       Optional<Users> optionalUser = Optional.ofNullable(usersService.findById(id));
       if(optionalUser.isPresent()) {
           Users user = optionalUser.get();
	   user.setUsername(userDetails.getUsername());
	   user.setEmail(userDetails.getEmail());
	   user.setPassword(userDetails.getPassword());
	   user.setFirstName(userDetails.getFirstName());
	   user.setLastName(userDetails.getLastName());
           
	   Users updatedUser = usersService.save(user);
	   return ResponseEntity.ok(updatedUser);
       } else {
	    return ResponseEntity.notFound().build();
   	}
   }
   @DeleteMapping("/{id}")
   public void deleteUser(@PathVariable Long id) {
       usersService.deleteById(id);
   }
/*
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

 */
}
