package com.diginamic.apijava.dto;

public class AbsenceOrganizationScoreDto {

	private Integer publicHolidayAcquired;
	private Integer publicHolidayTaken;
	private Integer publicHolidaySolde;
	private Integer employerRttAcquired;
	private Integer employerRttTaken;
	private Integer employerRttSolde;
	
	public AbsenceOrganizationScoreDto(Integer publicHolidayAcquired, Integer publicHolidayTaken,
			Integer publicHolidaySolde, Integer employerRttAcquired, Integer employerRttTaken,
			Integer employerRttSolde) {
		super();
		this.publicHolidayAcquired = publicHolidayAcquired;
		this.publicHolidayTaken = publicHolidayTaken;
		this.publicHolidaySolde = publicHolidaySolde;
		this.employerRttAcquired = employerRttAcquired;
		this.employerRttTaken = employerRttTaken;
		this.employerRttSolde = employerRttSolde;
	}

	public Integer getPublicHolidayAcquired() {
		return publicHolidayAcquired;
	}

	public void setPublicHolidayAcquired(Integer publicHolidayAcquired) {
		this.publicHolidayAcquired = publicHolidayAcquired;
	}

	public Integer getPublicHolidayTaken() {
		return publicHolidayTaken;
	}

	public void setPublicHolidayTaken(Integer publicHolidayTaken) {
		this.publicHolidayTaken = publicHolidayTaken;
	}

	public Integer getPublicHolidaySolde() {
		return publicHolidaySolde;
	}

	public void setPublicHolidaySolde(Integer publicHolidaySolde) {
		this.publicHolidaySolde = publicHolidaySolde;
	}

	public Integer getEmployerRttAcquired() {
		return employerRttAcquired;
	}

	public void setEmployerRttAcquired(Integer employerRttAcquired) {
		this.employerRttAcquired = employerRttAcquired;
	}

	public Integer getEmployerRttTaken() {
		return employerRttTaken;
	}

	public void setEmployerRttTaken(Integer employerRttTaken) {
		this.employerRttTaken = employerRttTaken;
	}

	public Integer getEmployerRttSolde() {
		return employerRttSolde;
	}

	public void setEmployerRttSolde(Integer employerRttSolde) {
		this.employerRttSolde = employerRttSolde;
	}
	
}
