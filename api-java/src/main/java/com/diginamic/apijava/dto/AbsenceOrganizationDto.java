package com.diginamic.apijava.dto;

public class AbsenceOrganizationDto {
	
	private Integer id;
	private String date;
	private String reason;
	private String absenceStatus;
	private String absenceType;
	private String organization;
	
	public AbsenceOrganizationDto(Integer id, String date, String reason, String absenceStatus, String absenceType,
			String organization) {
		super();
		this.id = id;
		this.date = date;
		this.reason = reason;
		this.absenceStatus = absenceStatus;
		this.absenceType = absenceType;
		this.organization = organization;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getAbsenceStatus() {
		return absenceStatus;
	}

	public void setAbsenceStatus(String absenceStatus) {
		this.absenceStatus = absenceStatus;
	}

	public String getAbsenceType() {
		return absenceType;
	}

	public void setAbsenceType(String absenceType) {
		this.absenceType = absenceType;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
}
