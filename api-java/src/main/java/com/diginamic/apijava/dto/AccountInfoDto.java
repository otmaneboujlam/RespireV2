package com.diginamic.apijava.dto;

import java.util.List;

public class AccountInfoDto {

	private Integer id;
	private String firstName;
	private String lastName;
	private String email;
	private String image;
	private String paidHolidayLastYear;
	private String paidHolidayThisYear;
	private String employeeRtt;
	private String group;
	private String department;
	private String organization;
	private String startDate;
	private List<String> roles;
	
	public AccountInfoDto(Integer id, String firstName, String lastName, String email, String image,
			String paidHolidayLastYear, String paidHolidayThisYear, String employeeRtt, String group, String department,
			String organization, String startDate, List<String> roles) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.image = image;
		this.paidHolidayLastYear = paidHolidayLastYear;
		this.paidHolidayThisYear = paidHolidayThisYear;
		this.employeeRtt = employeeRtt;
		this.group = group;
		this.department = department;
		this.organization = organization;
		this.startDate = startDate;
		this.roles = roles;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPaidHolidayLastYear() {
		return paidHolidayLastYear;
	}

	public void setPaidHolidayLastYear(String paidHolidayLastYear) {
		this.paidHolidayLastYear = paidHolidayLastYear;
	}

	public String getPaidHolidayThisYear() {
		return paidHolidayThisYear;
	}

	public void setPaidHolidayThisYear(String paidHolidayThisYear) {
		this.paidHolidayThisYear = paidHolidayThisYear;
	}

	public String getEmployeeRtt() {
		return employeeRtt;
	}

	public void setEmployeeRtt(String employeeRtt) {
		this.employeeRtt = employeeRtt;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
}
