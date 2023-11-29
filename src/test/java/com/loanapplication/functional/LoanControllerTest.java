package com.loanapplication.functional;

import static com.loanapplication.utils.MasterData.getLoanDTO;
import static com.loanapplication.utils.MasterData.getLoanDTOList;
import static com.loanapplication.utils.TestUtils.businessTestFile;
import static com.loanapplication.utils.TestUtils.currentTest;
import static com.loanapplication.utils.TestUtils.testReport;
import static com.loanapplication.utils.TestUtils.yakshaAssert;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
public class LoanControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private LoanService loanService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testCreateLoan() throws Exception {
		LoanDTO loanDTO = getLoanDTO();

		when(this.loanService.createLoan(loanDTO)).thenReturn(loanDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/loans")
				.content(MasterData.asJsonString(loanDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), (MasterData.asJsonString(loanDTO).length() > 0) ? "true" : "false",
				businessTestFile);
	}

	@Test
	public void testUpdateLoanStatus() throws Exception {
		Long loanId = 1L;
		String status = "APPROVED";
		LoanDTO loanDTO = getLoanDTO();

		when(this.loanService.updateLoanStatus(loanId, status)).thenReturn(loanDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/loans/" + loanId + "/status")
				.param("status", status).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), (result.getResponse().getContentAsString().length() > 0) ? "true" : "false",
				businessTestFile);
	}

	@Test
	public void testGetAllLoans() throws Exception {
		List<LoanDTO> loanDTOList = getLoanDTOList();

		when(this.loanService.getAllLoans()).thenReturn(loanDTOList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/loans").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(loanDTOList)) ? "true"
						: "false"),
				businessTestFile);
	}

	@Test
	public void testGetLoansByStatus() throws Exception {
		String status = "APPROVED";
		List<LoanDTO> loanDTOList = getLoanDTOList();

		when(this.loanService.getLoansByStatus(status)).thenReturn(loanDTOList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/loans/status").param("status", status)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(loanDTOList)) ? "true"
						: "false"),
				businessTestFile);
	}
}
