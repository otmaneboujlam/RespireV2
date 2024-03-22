package com.diginamic.apijava.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@EnableMethodSecurity(securedEnabled = true)
public class HelloController {
	
	@Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
	@GetMapping("/user")
	public String helloUser() {
		return "Hello admin or manager or user";
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
	@GetMapping("/manager")
	public String helloManager() {
		return "Hello admin or manager";
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/admin")
	public String helloAdmin() {
		return "Hello admin";
	}

}
