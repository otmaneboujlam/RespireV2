package com.diginamic.apijava.entity;

import java.time.LocalDate;

import com.diginamic.apijava.enums.AbsenceStatus;
import com.diginamic.apijava.enums.AbsenceType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Absence implements Comparable<Absence>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private LocalDate startDate;
	
	@Column(nullable = false)
	private LocalDate endDate;
	
	private String reason;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Account account;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AbsenceStatus absenceStatus;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AbsenceType absenceType;
	
	public int compareTo(Absence autre) {
		if (this.startDate.isAfter(autre.getStartDate())){
			return 1;
		}
		if (this.startDate.isBefore(autre.getStartDate())){
			return -1;
		}
		return 0;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
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

	public AbsenceType getAbsenceType() {
		return absenceType;
	}

	public void setAbsenceType(AbsenceType absenceType) {
		this.absenceType = absenceType;
	}

	public AbsenceStatus getAbsenceStatus() {
		return absenceStatus;
	}

	public void setAbsenceStatus(AbsenceStatus absenceStatus) {
		this.absenceStatus = absenceStatus;
	}

}
