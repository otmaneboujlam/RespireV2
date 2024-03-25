package com.diginamic.apijava.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Organization {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private Integer publicHoliday;
	
	@Column(nullable = false)
	private Integer employerRtt;
	
	@OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Department> departments = new ArrayList<Department>();
	
	@OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<AbsenceOrganization> absencesOrganization = new ArrayList<AbsenceOrganization>();

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

	public List<AbsenceOrganization> getAbsencesOrganization() {
		return absencesOrganization;
	}

	public void setAbsencesOrganization(List<AbsenceOrganization> absencesOrganization) {
		this.absencesOrganization = absencesOrganization;
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

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}
	
}
