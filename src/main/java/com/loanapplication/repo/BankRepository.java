package com.loanapplication.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loanapplication.entity.Bank;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
}
