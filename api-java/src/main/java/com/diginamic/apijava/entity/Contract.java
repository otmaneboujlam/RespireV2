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
public class Contract {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private Float paidHoliday;
	
	@Column(nullable = false)
	private Integer employeeRtt;
	
	@OneToMany(mappedBy = "contract", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Account> accounts = new ArrayList<Account>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getPaidHoliday() {
		return paidHoliday;
	}

	public void setPaidHoliday(Float paidHoliday) {
		this.paidHoliday = paidHoliday;
	}

	public Integer getEmployeeRtt() {
		return employeeRtt;
	}

	public void setEmployeeRtt(Integer employeeRtt) {
		this.employeeRtt = employeeRtt;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

}
