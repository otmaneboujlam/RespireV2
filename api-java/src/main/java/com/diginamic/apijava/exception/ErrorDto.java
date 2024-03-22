package com.diginamic.apijava.exception;

import java.time.LocalDateTime;

public class ErrorDto {

	private final Integer statusCode;
	private final LocalDateTime localDateTime;
	private final String message;
	private final String description;
	
	public ErrorDto(Integer statusCode, String message, String description) {
		super();
		this.statusCode = statusCode;
		this.localDateTime = LocalDateTime.now();
		this.message = message;
		this.description = description;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public String getMessage() {
		return message;
	}

	public String getDescription() {
		return description;
	}
	
}
