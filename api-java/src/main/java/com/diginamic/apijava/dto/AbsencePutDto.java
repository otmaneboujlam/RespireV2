package com.diginamic.apijava.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AbsencePutDto {
	
	@NotNull
	private Integer id;
	
	@NotBlank
	private String startDate;
	
	@NotBlank
	private String endDate;
	
	@NotBlank
	private String absenceType;
	
	private String reason;

	public AbsencePutDto(@NotNull Integer id, @NotBlank String startDate, @NotBlank String endDate,
			@NotBlank String absenceType, String reason) {
		super();
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.absenceType = absenceType;
		this.reason = reason;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
