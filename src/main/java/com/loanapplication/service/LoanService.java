package com.loanapplication.service;

import java.util.List;

import com.loanapplication.dto.LoanDTO;

public interface LoanService {
	LoanDTO createLoan(LoanDTO loanDTO);

	LoanDTO updateLoanStatus(Long loanId, String newStatus);

	List<LoanDTO> getAllLoans();

	List<LoanDTO> getLoansByStatus(String status);

	LoanDTO getLoanById(Long loanId);
}
