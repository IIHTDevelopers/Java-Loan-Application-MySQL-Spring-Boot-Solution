package com.loanapplication.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loanapplication.dto.BankDTO;
import com.loanapplication.service.BankService;

@RestController
@RequestMapping("/api/banks")
public class BankController {

	private final BankService bankService;

	@Autowired
	public BankController(BankService bankService) {
		this.bankService = bankService;
	}

	@PostMapping
	public ResponseEntity<BankDTO> createBank(@RequestBody @Valid BankDTO bankDTO) {
		BankDTO createdBank = bankService.createBank(bankDTO);
		return new ResponseEntity<>(createdBank, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<BankDTO>> getAllBanks() {
		List<BankDTO> banks = bankService.getAllBanks();
		return new ResponseEntity<>(banks, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BankDTO> getBankById(@PathVariable Long id) {
		BankDTO bankDTO = bankService.getBankById(id);
		return new ResponseEntity<>(bankDTO, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<BankDTO> updateBank(@PathVariable Long id, @RequestBody BankDTO bankDTO) {
		BankDTO updatedBank = bankService.updateBank(id, bankDTO);
		return new ResponseEntity<>(updatedBank, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBank(@PathVariable Long id) {
		bankService.deleteBank(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
