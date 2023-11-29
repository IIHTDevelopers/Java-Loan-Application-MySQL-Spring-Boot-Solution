package com.loanapplication.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

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
