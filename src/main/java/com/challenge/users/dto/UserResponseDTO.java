package com.challenge.users.dto;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import com.challenge.users.model.UserModel;
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

	private UUID id;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdDt;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastlogin;
	private String token; //revisar
	private Boolean isActive;


	public static UserResponseDTO entityToDTO(UserModel usermodel) {
		return UserResponseDTO.builder().id(usermodel.getId()).name(usermodel.getName())
				.email(usermodel.getEmail()).password(usermodel.getPassword())
				.phones(usermodel.getPhones().stream()
						.map(phone -> PhoneDTO.builder()
								.number(phone.getNumber())
								.cityCode(phone.getCityCode())
								.countryCode(phone.getCountryCode())
								.build())
						.collect(Collectors.toList()))
				.lastlogin(usermodel.getLastLogin()).createdDt(usermodel.getCreatedDt())
				.token(usermodel.getToken()).isActive(usermodel.getIsActive()).build();

	}
}
