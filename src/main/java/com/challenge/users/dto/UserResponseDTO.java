package com.challenge.users.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class UserResponseDTO extends UserDTO{
	
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdDt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastlogin;
	private String token; //revisar
	private Boolean isActive;
	
}
