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

import com.loanapplication.dto.LoanDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class LoanBoundaryTest {

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
	public void testApplicantNameNotNull() throws IOException {
		LoanDTO loanDTO = new LoanDTO();
		loanDTO.setApplicantName(null);
		Set<ConstraintViolation<LoanDTO>> violations = validator.validate(loanDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testApplicantNameNotBlank() throws IOException {
		LoanDTO loanDTO = new LoanDTO();
		loanDTO.setApplicantName("");
		Set<ConstraintViolation<LoanDTO>> violations = validator.validate(loanDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testBankInfoNotNull() throws IOException {
		LoanDTO loanDTO = new LoanDTO();
		loanDTO.setBankInfo(null);
		Set<ConstraintViolation<LoanDTO>> violations = validator.validate(loanDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testStatusNotNull() throws IOException {
		LoanDTO loanDTO = new LoanDTO();
		loanDTO.setStatus(null);
		Set<ConstraintViolation<LoanDTO>> violations = validator.validate(loanDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testStatusNotBlank() throws IOException {
		LoanDTO loanDTO = new LoanDTO();
		loanDTO.setStatus("");
		Set<ConstraintViolation<LoanDTO>> violations = validator.validate(loanDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}
}
