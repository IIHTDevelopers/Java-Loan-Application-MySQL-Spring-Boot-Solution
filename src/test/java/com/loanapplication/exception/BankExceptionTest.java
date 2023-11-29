package com.loanapplication.exception;

import static com.loanapplication.utils.TestUtils.currentTest;
import static com.loanapplication.utils.TestUtils.exceptionTestFile;
import static com.loanapplication.utils.TestUtils.testReport;
import static com.loanapplication.utils.TestUtils.yakshaAssert;
import static org.mockito.Mockito.when;

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

import com.loanapplication.controller.BankController;
import com.loanapplication.dto.BankDTO;
import com.loanapplication.service.BankService;
import com.loanapplication.utils.MasterData;

@WebMvcTest(BankController.class)
@AutoConfigureMockMvc
public class BankExceptionTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BankService bankService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testCreateBankInvalidDataException() throws Exception {
		BankDTO bankDTO = new BankDTO(); // Create an invalid BankDTO

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/banks")
				.content(MasterData.asJsonString(bankDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getStatus() == HttpStatus.BAD_REQUEST.value() ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testGetBankByIdNotFoundException() throws Exception {
		Long bankId = 1L;
		ErrorResponse exResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Bank not found");

		when(this.bankService.getBankById(bankId)).thenThrow(new NotFoundException("Bank not found"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/banks/" + bankId)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? "true" : "false"),
				exceptionTestFile);
	}
}
