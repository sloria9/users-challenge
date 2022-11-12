package com.challenge.users.handler;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.challenge.users.dto.ErrorsDTO;
import com.challenge.users.dto.ExceptionDTO;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { Exception.class })
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ErrorsDTO> handleGlobalExceptions(Exception exception, WebRequest request) {
		ExceptionDTO errorDetails = new ExceptionDTO();
		final ErrorsDTO errors = new ErrorsDTO();
		errorDetails.setCode(500);
		errorDetails.setDetails(" an error has occurred please contact system administrator ");
		errorDetails.setTimestamp(new Date());
		errors.addException(errorDetails);

		return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
	}


	@ExceptionHandler(value = { BadCredentialsException.class })
	public ResponseEntity<ErrorsDTO> handleBadCredentialsException(BadCredentialsException exception, WebRequest request) {
		ExceptionDTO errorDetails = new ExceptionDTO();
		final ErrorsDTO errors = new ErrorsDTO();
		errorDetails.setCode(500);
		errorDetails.setDetails(exception.getMessage());
		errorDetails.setTimestamp(new Date());
		errors.addException(errorDetails);

		return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorsDTO errors = new ErrorsDTO(new ArrayList<>());
		ExceptionDTO errorDetails = new ExceptionDTO();
		errorDetails.setCode(HttpStatus.BAD_REQUEST.value());
		errorDetails.setDetails(ex.getFieldError().getDefaultMessage());
		errorDetails.setTimestamp(new Date());
		errors.addException(errorDetails);
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(NullPointerException.class)
	public final ResponseEntity<ErrorsDTO> handleNullPointerExceptions(NullPointerException ex, WebRequest request) {
		ErrorsDTO errors = new ErrorsDTO(new ArrayList<>());
		ExceptionDTO errorDetails = new ExceptionDTO();
		errorDetails.setCode(HttpStatus.BAD_REQUEST.value());
		errorDetails.setDetails(ex.getMessage());
		errorDetails.setTimestamp(new Date());
		errors.addException(errorDetails);
		return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
	}


}
