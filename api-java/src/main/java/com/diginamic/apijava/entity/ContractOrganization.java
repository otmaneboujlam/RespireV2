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
public class ContractOrganization {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private Integer publicHoliday;
	
	@Column(nullable = false)
	private Integer employerRtt;
	
	@OneToMany(mappedBy = "contractOrganization", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Organization> organizations = new ArrayList<Organization>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public List<Organization> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(List<Organization> organizations) {
		this.organizations = organizations;
	}

}
