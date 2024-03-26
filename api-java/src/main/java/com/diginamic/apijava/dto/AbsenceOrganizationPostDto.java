package com.diginamic.apijava.dto;

import jakarta.validation.constraints.NotBlank;

public class AbsenceOrganizationPostDto {
	
	@NotBlank
	private String date;
	
	@NotBlank
	private String absenceOrganizationType;
	
	private String reason;

	@NotBlank
	private String organization;

	public AbsenceOrganizationPostDto(@NotBlank String date, @NotBlank String absenceOrganizationType, String reason,
			@NotBlank String organization) {
		super();
		this.date = date;
		this.absenceOrganizationType = absenceOrganizationType;
		this.reason = reason;
		this.organization = organization;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAbsenceOrganizationType() {
		return absenceOrganizationType;
	}

	public void setAbsenceOrganizationType(String absenceOrganizationType) {
		this.absenceOrganizationType = absenceOrganizationType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
}
