package com.sherm.pfinance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sherm.pfinance.models.Accounts;

public interface AccountsRepository extends JpaRepository<Accounts, String> {

}
