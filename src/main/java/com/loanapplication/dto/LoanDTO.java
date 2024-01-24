package com.loanapplication.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LoanDTO {
	private Long id;

	@NotBlank
	private String applicantName;

	@NotNull
	@Valid
	private BankDTO bankInfo;

	@NotBlank
	private String status;

	public LoanDTO() {
		super();
	}

	public LoanDTO(Long id, @NotBlank String applicantName, @NotBlank BankDTO bankInfo, @NotBlank String status) {
		super();
		this.id = id;
		this.applicantName = applicantName;
		this.bankInfo = bankInfo;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public BankDTO getBankInfo() {
		return bankInfo;
	}

	public void setBankInfo(BankDTO bankInfo) {
		this.bankInfo = bankInfo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "LoanDTO [id=" + id + ", applicantName=" + applicantName + ", bankInfo=" + bankInfo + ", status="
				+ status + "]";
	}
}
