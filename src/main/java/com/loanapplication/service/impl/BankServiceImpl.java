package com.loanapplication.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loanapplication.dto.BankDTO;
import com.loanapplication.entity.Bank;
import com.loanapplication.exception.NotFoundException;
import com.loanapplication.repo.BankRepository;
import com.loanapplication.service.BankService;

@Service
public class BankServiceImpl implements BankService {

	private final BankRepository bankRepository;

	@Autowired
	public BankServiceImpl(BankRepository bankRepository) {
		this.bankRepository = bankRepository;
	}

	@Override
	public BankDTO createBank(BankDTO bankDTO) {
		Bank bank = new Bank();
		BeanUtils.copyProperties(bankDTO, bank);
		bank = bankRepository.save(bank);
		return convertToDTO(bank);
	}

	@Override
	public List<BankDTO> getAllBanks() {
		List<Bank> banks = bankRepository.findAll();
		return banks.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public BankDTO getBankById(Long bankId) {
		Optional<Bank> bank = bankRepository.findById(bankId);
		if (bank.isPresent()) {
			return convertToDTO(bank.get());
		} else {
			throw new NotFoundException("Bank not found");
		}
	}

	@Override
	public boolean deleteBank(Long bankId) {
		Optional<Bank> optionalBank = bankRepository.findById(bankId);
		if (optionalBank.isPresent()) {
			bankRepository.deleteById(bankId);
			return true;
		} else {
			throw new NotFoundException("Bank not found");
		}
	}

	@Override
	public BankDTO updateBank(Long bankId, BankDTO bankDTO) {
		Optional<Bank> optionalBank = bankRepository.findById(bankId);
		if (optionalBank.isPresent()) {
			Bank bank = optionalBank.get();
			BeanUtils.copyProperties(bankDTO, bank);
			bank.setId(bankId);
			bankRepository.save(bank);
			return bankDTO;
		} else {
			throw new NotFoundException("Bank not found");
		}
	}

	@Override
	public List<BankDTO> getAllBanksWithInterestRateBelow(double interestRate) {
		List<Bank> banks = bankRepository.findAllByInterestRateLessThan(interestRate);
		return banks.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	private BankDTO convertToDTO(Bank bank) {
		BankDTO bankDTO = new BankDTO();
		BeanUtils.copyProperties(bank, bankDTO);
		return bankDTO;
	}
}
