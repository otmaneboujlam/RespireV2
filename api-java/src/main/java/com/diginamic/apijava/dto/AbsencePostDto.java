package com.diginamic.apijava.dto;

import jakarta.validation.constraints.NotBlank;

public class AbsencePostDto {
	
	@NotBlank
	private String startDate;
	
	@NotBlank
	private String endDate;
	
	@NotBlank
	private String absenceType;
	
	private String reason;

	public AbsencePostDto(@NotBlank String startDate, @NotBlank String endDate, @NotBlank String absenceType,
			String reason) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.absenceType = absenceType;
		this.reason = reason;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getAbsenceType() {
		return absenceType;
	}

	public void setAbsenceType(String absenceType) {
		this.absenceType = absenceType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
