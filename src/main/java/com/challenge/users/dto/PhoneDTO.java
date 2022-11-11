package com.challenge.users.dto;

import com.challenge.users.model.PhoneModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhoneDTO {

	private Long number;
	private Integer cityCode;
	private String countryCode;

	public static PhoneDTO modelToDto(PhoneModel phoneModel) {
		return PhoneDTO.builder().number(phoneModel.getNumber())
				.cityCode(phoneModel.getCityCode())
				.countryCode(phoneModel.getCountryCode()).build();
	}

}
