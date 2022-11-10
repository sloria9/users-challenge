package com.challenge.users.model;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.challenge.users.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserModel {

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;
	
    @Column(name = "token")
    private String token;

    @Column(name = "created")
    private Date created;
    
    @Column(name = "last_login")
    private Date lastLogin;
    
    @Column(name = "is_active")
    private Boolean isActive;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private List<PhoneModel> phones;

	public static UserModel dtoToModel(UserDTO userdto) {
		return UserModel.builder().name(userdto.getName()).email(userdto.getEmail())
				.password(userdto.getPassword()).phones(userdto.getPhones().stream()
						.map(phone -> PhoneModel.builder()
								.number(phone.getNumber())
								.cityCode(phone.getCityCode())
								.countryCode(phone.getCountryCode())
								.build())
						.collect(Collectors.toList())).build();
	}

}
