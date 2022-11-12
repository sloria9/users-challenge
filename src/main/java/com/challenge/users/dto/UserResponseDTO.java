package com.challenge.users.dto;

import java.util.Date;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy HH:mm:ss a", timezone = "GMT-3")
	private Date createdDt;
    private String token;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy HH:mm:ss a", timezone = "GMT-3")
	private Date lastlogin;
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
