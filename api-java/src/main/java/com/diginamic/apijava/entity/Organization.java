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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Organization {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private String name;
	
	@OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Account> accounts = new ArrayList<Account>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = true)
	private ContractOrganization contractOrganization;
	
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

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public ContractOrganization getContractOrganization() {
		return contractOrganization;
	}

	public void setContractOrganization(ContractOrganization contractOrganization) {
		this.contractOrganization = contractOrganization;
	}

	public List<AbsenceOrganization> getAbsencesOrganization() {
		return absencesOrganization;
	}

	public void setAbsencesOrganization(List<AbsenceOrganization> absencesOrganization) {
		this.absencesOrganization = absencesOrganization;
	}
	
}
