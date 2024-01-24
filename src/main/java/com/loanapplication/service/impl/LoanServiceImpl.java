package com.loanapplication.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.loanapplication.dto.BankDTO;
import com.loanapplication.dto.LoanDTO;
import com.loanapplication.entity.Bank;
import com.loanapplication.entity.Loan;
import com.loanapplication.exception.NotFoundException;
import com.loanapplication.repo.LoanRepository;
import com.loanapplication.service.LoanService;

import jakarta.transaction.Transactional;

@Service
public class LoanServiceImpl implements LoanService {

	private final LoanRepository loanRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	public LoanServiceImpl(LoanRepository loanRepository) {
		this.loanRepository = loanRepository;
	}

	@Override
	@Transactional
	public LoanDTO createLoan(LoanDTO loanDTO) {
		Loan loan = convertToEntity(loanDTO);
		loan = loanRepository.save(loan);
		return convertToDTO(loan);
	}

	@Override
	@Transactional
	public LoanDTO updateLoanStatus(Long loanId, String newStatus) {
		Optional<Loan> optionalLoan = loanRepository.findById(loanId);
		if (optionalLoan.isPresent()) {
			Loan loan = optionalLoan.get();
			loan.setStatus(newStatus);
			loanRepository.save(loan);
			return convertToDTO(loan);
		} else {
			throw new NotFoundException("Loan not found");
		}
	}

	@Override
	public Page<LoanDTO> getAllLoans(Pageable pageable) {
		Page<Loan> loanPage = loanRepository.findAllByOrderByApplicantNameAsc(pageable);
		return loanPage.map(loan -> modelMapper.map(loan, LoanDTO.class));
	}

	@Override
	public List<LoanDTO> getLoansByStatus(String status) {
		List<Loan> loans = loanRepository.findByStatus(status);
		return loans.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public LoanDTO getLoanById(Long loanId) {
		Optional<Loan> loan = loanRepository.findById(loanId);
		if (loan.isPresent()) {
			return convertToDTO(loan.get());
		} else {
			throw new NotFoundException("Loan not found");
		}
	}

	private LoanDTO convertToDTO(Loan loan) {
		LoanDTO loanDTO = new LoanDTO();
		loanDTO.setId(loan.getId());
		loanDTO.setApplicantName(loan.getApplicantName());
		loanDTO.setStatus(loan.getStatus());
		BankDTO bankDTO = new BankDTO(loan.getBankInfo().getId(), loan.getBankInfo().getName(),
				loan.getBankInfo().getLoanType(), loan.getBankInfo().getInterestRate());
		loanDTO.setBankInfo(bankDTO);
		return loanDTO;
	}

	private Loan convertToEntity(LoanDTO loanDTO) {
		Loan loan = new Loan();
		loan.setApplicantName(loanDTO.getApplicantName());
		loan.setStatus(loanDTO.getStatus());
		Bank bank = new Bank(loanDTO.getBankInfo().getId(), loanDTO.getBankInfo().getName(),
				loanDTO.getBankInfo().getLoanType(), loanDTO.getBankInfo().getInterestRate());
		loan.setBankInfo(bank);
		return loan;
	}
}
