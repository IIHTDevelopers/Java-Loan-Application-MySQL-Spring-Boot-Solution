package com.loanapplication.exception;

import static com.loanapplication.utils.TestUtils.currentTest;
import static com.loanapplication.utils.TestUtils.exceptionTestFile;
import static com.loanapplication.utils.TestUtils.testReport;
import static com.loanapplication.utils.TestUtils.yakshaAssert;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.loanapplication.controller.LoanController;
import com.loanapplication.dto.LoanDTO;
import com.loanapplication.service.LoanService;
import com.loanapplication.utils.MasterData;

@WebMvcTest(LoanController.class)
@AutoConfigureMockMvc
public class LoanExceptionTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private LoanService loanService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testCreateLoanInvalidDataException() throws Exception {
		LoanDTO loanDTO = new LoanDTO();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/loans")
				.content(MasterData.asJsonString(loanDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getStatus() == HttpStatus.BAD_REQUEST.value() ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testUpdateLoanStatusNotFoundException() throws Exception {
		Long loanId = 5L;
		String status = "APPROVED";
		ErrorResponse exResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Loan not found");
		when(this.loanService.updateLoanStatus(loanId, status)).thenThrow(new NotFoundException("Loan not found"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/loans/" + loanId + "/status")
				.param("status", status).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testGetLoansByStatusNoContentException() throws Exception {
		String status = "APPROVED";
		when(this.loanService.getLoansByStatus(status)).thenReturn(Collections.emptyList());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/loans/status").param("status", status)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), (result.getResponse().getContentLength() == 0 ? "true" : "false"),
				exceptionTestFile);
	}
}
