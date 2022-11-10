package com.challenge.users.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.challenge.users.model.UserModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserDTO {

	private String name;

	@Email
	@NotEmpty
	private String email;

	@NotEmpty
	@Pattern(regexp = "(?=.*[A-Z]).*(?=.*[0-9])((\\D*\\d){2,})",
	message = " password must contain 2 uppercase and 2 digits ")
	@Size(min = 8, max = 12, message = "password must be of 6 to 12 length")
	private String password;

	private List<PhoneDTO> phones;

	public static UserDTO modelToDto(UserModel user) {
		return UserDTO.builder().name(user.getName()).email(user.getEmail())
				.password(user.getPassword()).phones(user.getPhones().stream()
						.map(phone -> PhoneDTO.builder()
								.number(phone.getNumber())
								.cityCode(phone.getCityCode())
								.countryCode(phone.getCountryCode())
								.build())
						.collect(Collectors.toList())).build();
	}

}
