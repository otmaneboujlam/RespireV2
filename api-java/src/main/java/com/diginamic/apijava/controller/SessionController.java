package com.diginamic.apijava.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diginamic.apijava.dto.AccountConnectionDto;
import com.diginamic.apijava.entity.Account;
import com.diginamic.apijava.repository.AccountRepository;
import com.diginamic.apijava.securityConfig.JWTConfig;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/signin")
public class SessionController {
	
	private JWTConfig jwtConfig;
	private AccountRepository activeUserRepository;
	private PasswordEncoder passwordEncoder;
	
	public SessionController(JWTConfig jwtConfig, AccountRepository activeUserRepository, PasswordEncoder passwordEncoder) {
		this.jwtConfig = jwtConfig;
		this.activeUserRepository = activeUserRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
	@PostMapping
	public ResponseEntity<?> create(@RequestBody @Valid AccountConnectionDto loginDto) {
				
		return this.activeUserRepository.findFirstByEmail(loginDto.getEmail())
				.filter(user -> passwordEncoder.matches(loginDto.getPassword(), user.getPassword()))
				.map(user -> ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, buildJWTCookie(user)).build())
				.orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	private String buildJWTCookie(Account user) {

		Keys.secretKeyFor(SignatureAlgorithm.HS512);
		
		String jetonJWT = Jwts.builder()
				.setSubject(user.getEmail())
				.addClaims(Map.of("roles", user.getRoles().stream().map(r -> r.getName()).toList()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpireIn() * 1000))
				.signWith(jwtConfig.getSecretKey())
				.compact();
		
		ResponseCookie tokenCookie = ResponseCookie.from(jwtConfig.getCookie(), jetonJWT)
				.httpOnly(true)
				.maxAge(jwtConfig.getExpireIn() * 1000)
				.path("/")
				.build();
		
		return tokenCookie.toString();
	}

}
