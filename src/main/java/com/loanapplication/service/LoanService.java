package com.loanapplication.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.loanapplication.dto.LoanDTO;

public interface LoanService {
	LoanDTO createLoan(LoanDTO loanDTO);

	LoanDTO updateLoanStatus(Long loanId, String newStatus);

	Page<LoanDTO> getAllLoans(Pageable pageable);

	List<LoanDTO> getLoansByStatus(String status);

	LoanDTO getLoanById(Long loanId);
}
