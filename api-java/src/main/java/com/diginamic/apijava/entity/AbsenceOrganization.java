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
public class AbsenceOrganization {
	
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
	private Organization organization;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private AbsenceOrganizationStatus absenceOrganizationStatus;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private AbsenceOrganizationType absenceOrganizationType;

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
