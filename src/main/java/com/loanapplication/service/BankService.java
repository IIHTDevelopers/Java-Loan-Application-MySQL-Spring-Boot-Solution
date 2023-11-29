package com.loanapplication.service;

import java.util.List;

import com.loanapplication.dto.BankDTO;

public interface BankService {
	BankDTO createBank(BankDTO bankDTO);

	List<BankDTO> getAllBanks();

	BankDTO getBankById(Long bankId);

	boolean deleteBank(Long bankId);

	BankDTO updateBank(Long bankId, BankDTO bankDTO);
}
