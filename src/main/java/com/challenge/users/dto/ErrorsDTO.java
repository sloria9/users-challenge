package com.challenge.users.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ErrorsDTO {

	private List<ExceptionDTO> listErrors;

	public void addException(final ExceptionDTO exception) {
		listErrors.add(exception);
	}

}
