package com.sherm.pfinance.controllers;
import com.sherm.pfinance.models.Users;
import com.sherm.pfinance.models.Transactions;
import com.sherm.pfinance.models.Categories;
import com.sherm.pfinance.models.Accounts;
import com.sherm.pfinance.services.CategoriesService;
import com.sherm.pfinance.services.AccountsService;
import com.sherm.pfinance.services.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import com.sherm.pfinance.services.UsersService;
import java.util.List;
import com.sherm.pfinance.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/transactions")
public class TransactionsController {

    @Autowired
    private TransactionsService transactionService;
    @Autowired
    private UsersService userService;
    @Autowired 
    private AccountsService accountService;
    @Autowired
    private CategoriesService categorieService;

    @GetMapping
    public List<Transactions> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public Transactions getTransactionById(@PathVariable String id) {
        return transactionService.getTransactionById(id);
    }

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody Transactions transaction) {
        Optional<Users> optionalUser = Optional.ofNullable(userService.findById(transaction.getUser().getUser_id()));
        Optional<Accounts> optionalAccount = Optional.ofNullable(accountService.findById(transaction.getAccount().getAccountId()));

	Optional<Categories> optionalCategorie = Optional.ofNullable(categorieService.findById(transaction.getCategory().getCategory_id()));
 
	//Add category here
	if(!optionalUser.isPresent()) {
            ErrorResponse error = new ErrorResponse("User specified cannot be found by ID");
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error); 
	}
        if(!optionalAccount.isPresent()) {
 	    ErrorResponse error = new ErrorResponse("Account cannot be found via provided ID");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
        transaction.setUser(optionalUser.get());
	transaction.setAccount(optionalAccount.get());
	Transactions savedTransaction = transactionService.saveTransaction(transaction);
        return ResponseEntity.ok(savedTransaction); 
	
    }

    @PutMapping("/{id}")
    public Transactions updateTransaction(@PathVariable String id, @RequestBody Transactions transaction) {
        Transactions existingTransaction = transactionService.getTransactionById(id);
        if (existingTransaction != null) {
            transaction.setTransaction_id(id);
            return transactionService.saveTransaction(transaction);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable String id) {
        transactionService.deleteTransaction(id);
    }
}
