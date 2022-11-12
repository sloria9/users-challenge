package com.challenge.users.dto;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExceptionDTO {
	
    private Date timestamp;

    private int code;

    private String details;
}
