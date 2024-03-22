package com.diginamic.apijava.exception;

import java.util.List;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public class InvalidEntityErrorDto extends ErrorDto {

	private final List<ObjectError> globalErrors;
	private final List<FieldError> fielderrors;
	
	public InvalidEntityErrorDto(Integer statusCode, String message, String description, List<ObjectError> globalErrors,
			List<FieldError> fielderrors) {
		super(statusCode, message, description);
		this.globalErrors = globalErrors;
		this.fielderrors = fielderrors;
	}
	
	public List<ObjectError> getGlobalErrors() {
		return globalErrors;
	}
	public List<FieldError> getFielderrors() {
		return fielderrors;
	}
	
}
