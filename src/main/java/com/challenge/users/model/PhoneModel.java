package com.challenge.users.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "phones")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhoneModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @Column(name = "number")
    private String number;
    
    @Column(name = "cityCode")
    private String cityCode;
    
    @Column(name = "countryCode")
    private String countryCode;
}
