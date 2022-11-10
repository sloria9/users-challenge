package com.challenge.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhoneDTO {
	
    private Long id;
    private String number;
    private String cityCode;
    private String countryCode;
    
}
