package com.diginamic.apijava.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private String firstname;
	
	@Column(nullable = false)
	private String lastname;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	private String image;
	
	@ManyToMany
	private List<Role> roles = new ArrayList<Role>();
	
	@OneToMany(mappedBy = "account")
	@JsonIgnore
	private List<Absence> absences = new ArrayList<Absence>();
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private Department department;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private Contract contract;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private Organization organization;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<Absence> getAbsences() {
		return absences;
	}

	public void setAbsences(List<Absence> absences) {
		this.absences = absences;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	
}
