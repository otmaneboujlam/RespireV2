package com.diginamic.apijava.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AbsenceOrganizationPutDto {
	
	@NotNull
	private Integer id;
	
	@NotBlank
	private String date;
	
	@NotBlank
	private String absenceOrganizationType;
	
	private String reason;

	@NotBlank
	private String organization;

	public AbsenceOrganizationPutDto(@NotNull Integer id, @NotBlank String date,
			@NotBlank String absenceOrganizationType, String reason, @NotBlank String organization) {
		super();
		this.id = id;
		this.date = date;
		this.absenceOrganizationType = absenceOrganizationType;
		this.reason = reason;
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
