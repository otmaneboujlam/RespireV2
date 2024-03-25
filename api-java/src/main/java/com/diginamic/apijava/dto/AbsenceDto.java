package com.diginamic.apijava.dto;

public class AbsenceDto {
	
	private Integer id;
	private String startDate;
	private String endDate;
	private String reason;
	private String absenceStatus;
	private String absenceType;
	private String account;
	
	public AbsenceDto(Integer id, String startDate, String endDate, String reason, String absenceStatus,
			String absenceType, String account) {
		super();
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.reason = reason;
		this.absenceStatus = absenceStatus;
		this.absenceType = absenceType;
		this.account = account;
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

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
}
