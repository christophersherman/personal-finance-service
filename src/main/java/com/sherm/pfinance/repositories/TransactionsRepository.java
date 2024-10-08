package com.sherm.pfinance.repositories;

import com.sherm.pfinance.models.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, String> {

}
