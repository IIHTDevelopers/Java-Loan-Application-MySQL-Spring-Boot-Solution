package com.loanapplication.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "loan")
public class Loan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String applicantName;

	@ManyToOne
	@JoinColumn(name = "bank_id")
	private Bank bankInfo;

	private String status;

	public Loan() {
		super();
	}

	public Loan(Long id, @NotBlank String applicantName, Bank bankInfo, String status) {
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

	public Bank getBankInfo() {
		return bankInfo;
	}

	public void setBankInfo(Bank bankInfo) {
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
		return "Loan [id=" + id + ", applicantName=" + applicantName + ", bankInfo=" + bankInfo + ", status=" + status
				+ "]";
	}
}
