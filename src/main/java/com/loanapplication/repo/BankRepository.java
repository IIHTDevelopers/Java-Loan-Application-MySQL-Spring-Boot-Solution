package com.loanapplication.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loanapplication.entity.Bank;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

	List<Bank> findAllByInterestRateLessThan(double interestRate);
}
