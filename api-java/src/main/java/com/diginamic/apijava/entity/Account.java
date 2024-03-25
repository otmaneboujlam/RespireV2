package com.diginamic.apijava.entity;

import java.time.LocalDate;
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
	private LocalDate startDate;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	private String image;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Role> roles = new ArrayList<Role>();
	
	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Absence> absences = new ArrayList<Absence>();
	
	@Column(nullable = false)
	private Float paidHoliday;
	
	@Column(nullable = false)
	private Integer employeeRtt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = true)
	private Groupe groupe;

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

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public Groupe getGroupe() {
		return groupe;
	}

	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}
	
}
