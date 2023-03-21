package com.sherm.pfinance.services;

import com.sherm.pfinance.models.Transactions;
import com.sherm.pfinance.repositories.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionsService {

    @Autowired
    private TransactionsRepository transactionRepository;

    public List<Transactions> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transactions getTransactionById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    public Transactions saveTransaction(Transactions transaction) {
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}
