package com.diginamic.apijava.dto;

public class AbsenceScoreDto {

	private Float paidHolidayLastYearAcquired;
	private Float paidHolidayThisYearAcquired;
	private Float paidHolidayLastYearTaken;
	private Float paidHolidayThisYearTaken;
	private Float paidHolidayLastYearSolde;
	private Float paidHolidayThisYearSolde;
	private Integer employeeRttAcquired;
	private Integer employeeRttTaken;
	private Integer employeeRttSolde;
	
	public AbsenceScoreDto(Float paidHolidayLastYearAcquired, Float paidHolidayThisYearAcquired,
			Float paidHolidayLastYearTaken, Float paidHolidayThisYearTaken, Float paidHolidayLastYearSolde,
			Float paidHolidayThisYearSolde, Integer employeeRttAcquired, Integer employeeRttTaken,
			Integer employeeRttSolde) {
		super();
		this.paidHolidayLastYearAcquired = paidHolidayLastYearAcquired;
		this.paidHolidayThisYearAcquired = paidHolidayThisYearAcquired;
		this.paidHolidayLastYearTaken = paidHolidayLastYearTaken;
		this.paidHolidayThisYearTaken = paidHolidayThisYearTaken;
		this.paidHolidayLastYearSolde = paidHolidayLastYearSolde;
		this.paidHolidayThisYearSolde = paidHolidayThisYearSolde;
		this.employeeRttAcquired = employeeRttAcquired;
		this.employeeRttTaken = employeeRttTaken;
		this.employeeRttSolde = employeeRttSolde;
	}

	public Float getPaidHolidayLastYearAcquired() {
		return paidHolidayLastYearAcquired;
	}

	public void setPaidHolidayLastYearAcquired(Float paidHolidayLastYearAcquired) {
		this.paidHolidayLastYearAcquired = paidHolidayLastYearAcquired;
	}

	public Float getPaidHolidayThisYearAcquired() {
		return paidHolidayThisYearAcquired;
	}

	public void setPaidHolidayThisYearAcquired(Float paidHolidayThisYearAcquired) {
		this.paidHolidayThisYearAcquired = paidHolidayThisYearAcquired;
	}

	public Float getPaidHolidayLastYearTaken() {
		return paidHolidayLastYearTaken;
	}

	public void setPaidHolidayLastYearTaken(Float paidHolidayLastYearTaken) {
		this.paidHolidayLastYearTaken = paidHolidayLastYearTaken;
	}

	public Float getPaidHolidayThisYearTaken() {
		return paidHolidayThisYearTaken;
	}

	public void setPaidHolidayThisYearTaken(Float paidHolidayThisYearTaken) {
		this.paidHolidayThisYearTaken = paidHolidayThisYearTaken;
	}

	public Float getPaidHolidayLastYearSolde() {
		return paidHolidayLastYearSolde;
	}

	public void setPaidHolidayLastYearSolde(Float paidHolidayLastYearSolde) {
		this.paidHolidayLastYearSolde = paidHolidayLastYearSolde;
	}

	public Float getPaidHolidayThisYearSolde() {
		return paidHolidayThisYearSolde;
	}

	public void setPaidHolidayThisYearSolde(Float paidHolidayThisYearSolde) {
		this.paidHolidayThisYearSolde = paidHolidayThisYearSolde;
	}

	public Integer getEmployeeRttAcquired() {
		return employeeRttAcquired;
	}

	public void setEmployeeRttAcquired(Integer employeeRttAcquired) {
		this.employeeRttAcquired = employeeRttAcquired;
	}

	public Integer getEmployeeRttTaken() {
		return employeeRttTaken;
	}

	public void setEmployeeRttTaken(Integer employeeRttTaken) {
		this.employeeRttTaken = employeeRttTaken;
	}

	public Integer getEmployeeRttSolde() {
		return employeeRttSolde;
	}

	public void setEmployeeRttSolde(Integer employeeRttSolde) {
		this.employeeRttSolde = employeeRttSolde;
	}
	
}
