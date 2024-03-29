package com.diginamic.apijava.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AbsenceStatusUpdateDto {
	
	@NotNull
	private Integer id;
	
	@NotBlank
	private String absenceStatus;
	
	public AbsenceStatusUpdateDto(Integer id, String absenceStatus) {
		super();
		this.id = id;
		this.absenceStatus = absenceStatus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAbsenceStatus() {
		return absenceStatus;
	}

	public void setAbsenceStatus(String absenceStatus) {
		this.absenceStatus = absenceStatus;
	}
	
}
