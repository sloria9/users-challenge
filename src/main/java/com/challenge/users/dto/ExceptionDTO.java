package com.challenge.users.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ExceptionDTO {
	private Date timestamp;
	private String code;
	private String detail;
}
