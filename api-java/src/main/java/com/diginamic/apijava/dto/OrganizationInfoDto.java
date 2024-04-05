package com.diginamic.apijava.dto;

public class OrganizationInfoDto {

	private Integer id;
	private String name;
	private Integer publicHoliday;
	private Integer employerRtt;
	
	public OrganizationInfoDto(Integer id, String name, Integer publicHoliday, Integer employerRtt) {
		super();
		this.id = id;
		this.name = name;
		this.publicHoliday = publicHoliday;
		this.employerRtt = employerRtt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPublicHoliday() {
		return publicHoliday;
	}

	public void setPublicHoliday(Integer publicHoliday) {
		this.publicHoliday = publicHoliday;
	}

	public Integer getEmployerRtt() {
		return employerRtt;
	}

	public void setEmployerRtt(Integer employerRtt) {
		this.employerRtt = employerRtt;
	}
	
}
