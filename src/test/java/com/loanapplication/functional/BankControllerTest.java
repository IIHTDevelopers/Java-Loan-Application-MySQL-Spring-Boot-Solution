package com.loanapplication.functional;

import static com.loanapplication.utils.MasterData.getBankDTO;
import static com.loanapplication.utils.MasterData.getBankDTOList;
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

import com.loanapplication.controller.BankController;
import com.loanapplication.dto.BankDTO;
import com.loanapplication.service.BankService;
import com.loanapplication.utils.MasterData;

@WebMvcTest(BankController.class)
@AutoConfigureMockMvc
public class BankControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BankService bankService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testCreateBank() throws Exception {
		BankDTO bankDTO = getBankDTO();

		when(this.bankService.createBank(bankDTO)).thenReturn(bankDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/banks")
				.content(MasterData.asJsonString(bankDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), (MasterData.asJsonString(bankDTO).length() > 0) ? "true" : "false",
				businessTestFile);
	}

	@Test
	public void testGetAllBanks() throws Exception {
		List<BankDTO> bankDTOList = getBankDTOList();

		when(this.bankService.getAllBanks()).thenReturn(bankDTOList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/banks").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(bankDTOList)) ? "true"
						: "false"),
				businessTestFile);
	}

	@Test
	public void testGetBankById() throws Exception {
		long bankId = 1L;
		BankDTO bankDTO = getBankDTO();

		when(this.bankService.getBankById(bankId)).thenReturn(bankDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/banks/" + bankId)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(bankDTO)) ? "true"
						: "false"),
				businessTestFile);
	}

	@Test
	public void testUpdateBank() throws Exception {
		long bankId = 1L;
		BankDTO updatedBankDTO = getBankDTO();
		updatedBankDTO.setName("Updated Bank Name");

		when(this.bankService.updateBank(bankId, updatedBankDTO)).thenReturn(updatedBankDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/banks/" + bankId)
				.content(MasterData.asJsonString(updatedBankDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), (MasterData.asJsonString(updatedBankDTO).length() > 0) ? "true" : "false",
				businessTestFile);
	}

	@Test
	public void testDeleteBank() throws Exception {
		long bankId = 1L;

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/banks/" + bankId)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder);

		yakshaAssert(currentTest(), true, businessTestFile);
	}

	@Test
	public void testGetBanksWithInterestRateBelow() throws Exception {
		double interestRate = 9.5;
		List<BankDTO> bankDTOList = getBankDTOList();

		when(this.bankService.getAllBanksWithInterestRateBelow(interestRate)).thenReturn(bankDTOList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/banks/interest-rate-below/" + interestRate)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(bankDTOList)) ? "true"
						: "false"),
				businessTestFile);
	}
}
