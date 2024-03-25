package com.diginamic.apijava.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {
	
	@ExceptionHandler({jakarta.persistence.EntityNotFoundException.class})
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ErrorDto handleExceptionEntityNotFound(Exception exception, WebRequest request) {
		//exception.printStackTrace();
		return new ErrorDto(
				HttpStatus.NOT_FOUND.value(),  
				exception.getMessage(), 
				request.getDescription(false)
				);
	}
	
	@ExceptionHandler({StatusNotFoundException.class})
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ErrorDto handleExceptionStatusNotFoundException(Exception exception, WebRequest request) {
		//exception.printStackTrace();
		return new ErrorDto(
				HttpStatus.NOT_FOUND.value(),  
				exception.getMessage(), 
				request.getDescription(false)
				);
	}

	@ExceptionHandler({MethodArgumentNotValidException.class})
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public InvalidEntityErrorDto handleExceptionMethodArgumentNotValid(MethodArgumentNotValidException exception, WebRequest request) {
		//exception.printStackTrace();
		return new InvalidEntityErrorDto(
				HttpStatus.BAD_REQUEST.value(), 
				"Validation failed", 
				request.getDescription(false),
				exception.getBindingResult().getGlobalErrors(),
				exception.getBindingResult().getFieldErrors()
				);
	}
	
	@ExceptionHandler(RolesHaveNotBeenCreatedException.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorDto handleExceptionRolesHaveNotBeenCreated(Exception exception, WebRequest request) {
		//exception.printStackTrace();
		return new ErrorDto(
				HttpStatus.INTERNAL_SERVER_ERROR.value(), 
				exception.getMessage(), 
				request.getDescription(false)
				);
	}
	
	@ExceptionHandler(EntityAccessDeniedException.class)
	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	public ErrorDto handleExceptionEntityAccessDenied(Exception exception, WebRequest request) {
		//exception.printStackTrace();
		return new ErrorDto(
				HttpStatus.FORBIDDEN.value(), 
				exception.getMessage(), 
				request.getDescription(false)
				);
	}
		
}
