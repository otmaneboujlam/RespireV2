package com.diginamic.apijava.entity;

import java.time.LocalDate;

import com.diginamic.apijava.enums.AbsenceOrganizationStatus;
import com.diginamic.apijava.enums.AbsenceOrganizationType;

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
public class AbsenceOrganization {
	
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
	private Organization organization;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AbsenceOrganizationStatus absenceOrganizationStatus;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AbsenceOrganizationType absenceOrganizationType;

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

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public AbsenceOrganizationStatus getAbsenceOrganizationStatus() {
		return absenceOrganizationStatus;
	}

	public void setAbsenceOrganizationStatus(AbsenceOrganizationStatus absenceOrganizationStatus) {
		this.absenceOrganizationStatus = absenceOrganizationStatus;
	}

	public AbsenceOrganizationType getAbsenceOrganizationType() {
		return absenceOrganizationType;
	}

	public void setAbsenceOrganizationType(AbsenceOrganizationType absenceOrganizationType) {
		this.absenceOrganizationType = absenceOrganizationType;
	}

}
