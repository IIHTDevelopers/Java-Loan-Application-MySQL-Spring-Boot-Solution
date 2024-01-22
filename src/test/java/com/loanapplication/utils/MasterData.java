package com.loanapplication.utils;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.loanapplication.dto.BankDTO;
import com.loanapplication.dto.LoanDTO;

public class MasterData {

	public static BankDTO getBankDTO() {
		BankDTO bankDTO = new BankDTO();
		bankDTO.setId(1L);
		bankDTO.setName("ABC Bank");
		bankDTO.setLoanType("Home Loan");
		bankDTO.setInterestRate(5.5);
		return bankDTO;
	}

	public static List<BankDTO> getBankDTOList() {
		List<BankDTO> bankDTOList = new ArrayList<>();
		bankDTOList.add(getBankDTO());
		return bankDTOList;
	}

	public static LoanDTO getLoanDTO() {
		LoanDTO loanDTO = new LoanDTO();
		loanDTO.setId(1L);
		loanDTO.setApplicantName("John Doe");
		loanDTO.setBankInfo(getBankDTO());
		loanDTO.setStatus("Pending");
		return loanDTO;
	}

	public static List<LoanDTO> getLoanDTOList() {
		List<LoanDTO> loanDTOList = new ArrayList<>();
		loanDTOList.add(getLoanDTO());
		return loanDTOList;
	}

	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String randomStringWithSize(int size) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < size; i++) {
			s.append("A");
		}
		return s.toString();
	}
}
