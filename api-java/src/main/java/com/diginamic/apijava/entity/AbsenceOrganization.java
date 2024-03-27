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
public class AbsenceOrganization implements Comparable<AbsenceOrganization>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private LocalDate date;
	
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
	
	public int compareTo(AbsenceOrganization autre) {
		if (this.date.isAfter(autre.getDate())){
			return 1;
		}
		if (this.date.isBefore(autre.getDate())){
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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
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
