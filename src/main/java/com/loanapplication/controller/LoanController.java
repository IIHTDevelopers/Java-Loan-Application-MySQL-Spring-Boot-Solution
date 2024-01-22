package com.loanapplication.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.loanapplication.dto.LoanDTO;
import com.loanapplication.service.LoanService;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

	private final LoanService loanService;

	@Autowired
	public LoanController(LoanService loanService) {
		this.loanService = loanService;
	}

	@PostMapping
	public ResponseEntity<LoanDTO> createLoan(@RequestBody @Valid LoanDTO loanDTO) {
		LoanDTO createdLoan = loanService.createLoan(loanDTO);
		return new ResponseEntity<>(createdLoan, HttpStatus.CREATED);
	}

	@PutMapping("/{id}/status")
	public ResponseEntity<LoanDTO> updateLoanStatus(@PathVariable Long id, @RequestParam String status) {
		LoanDTO updatedLoan = loanService.updateLoanStatus(id, status);
		return new ResponseEntity<>(updatedLoan, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<Page<LoanDTO>> getAllLoans(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<LoanDTO> loans = loanService.getAllLoans(pageable);
		return new ResponseEntity<>(loans, HttpStatus.OK);
	}

	@GetMapping("/status")
	public ResponseEntity<List<LoanDTO>> getLoansByStatus(@RequestParam String status) {
		List<LoanDTO> loans = loanService.getLoansByStatus(status);
		return new ResponseEntity<>(loans, HttpStatus.OK);
	}
}
