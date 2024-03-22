package com.diginamic.apijava.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Absence {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private Date startDate;
	
	@Column(nullable = false)
	private Date endDate;
	
	private String reason;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Account account;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private AbsenceStatus absenceStatus;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private AbsenceType absenceType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public AbsenceStatus getAbsenceStatus() {
		return absenceStatus;
	}

	public void setAbsenceStatus(AbsenceStatus absenceStatus) {
		this.absenceStatus = absenceStatus;
	}

	public AbsenceType getAbsenceType() {
		return absenceType;
	}

	public void setAbsenceType(AbsenceType absenceType) {
		this.absenceType = absenceType;
	}

}
