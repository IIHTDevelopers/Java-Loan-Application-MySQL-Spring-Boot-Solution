package com.loanapplication.boundary;

import static com.loanapplication.utils.TestUtils.boundaryTestFile;
import static com.loanapplication.utils.TestUtils.currentTest;
import static com.loanapplication.utils.TestUtils.testReport;
import static com.loanapplication.utils.TestUtils.yakshaAssert;

import java.io.IOException;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.loanapplication.dto.BankDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class BankBoundaryTest {

	private static Validator validator;

	@BeforeAll
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testNameNotBlank() throws IOException {
		BankDTO bankDTO = new BankDTO();
		bankDTO.setName("");
		Set<ConstraintViolation<BankDTO>> violations = validator.validate(bankDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testLoanTypeNotBlank() throws IOException {
		BankDTO bankDTO = new BankDTO();
		bankDTO.setLoanType("");
		Set<ConstraintViolation<BankDTO>> violations = validator.validate(bankDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testInterestRateNotZero() throws IOException {
		BankDTO bankDTO = new BankDTO();
		bankDTO.setInterestRate(0.0);
		Set<ConstraintViolation<BankDTO>> violations = validator.validate(bankDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}
}
