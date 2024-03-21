package com.diginamic.apijava.dto;

import jakarta.validation.constraints.NotBlank;

public class AccountConnectionDto {
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String password;

	public AccountConnectionDto(@NotBlank String email, @NotBlank String password) {
		super();
		this.email = email;
		this.password = password;
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
	
	

}
