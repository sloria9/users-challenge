package com.challenge.users.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.challenge.users.dto.UserDTO;
import com.challenge.users.dto.UserResponseDTO;
import com.challenge.users.model.UserModel;
import com.challenge.users.repository.UserRepository;
import com.challenge.users.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

	private final UserRepository repositoryUser;
	@Autowired
    private PasswordEncoder passwordEncoder;

	@Override
	public UserResponseDTO signUp(UserDTO userdto) {
		String password = userdto.getPassword();

		String encrypPass = passwordEncoder.encode(password);

		userdto.setPassword(encrypPass);
		UserModel userNew = UserModel.dtoToModel(userdto);
		repositoryUser.save(userNew);
		return UserResponseDTO.builder()
				.name(userdto.getName()).email(userdto.getEmail())
				.password(userdto.getPassword()).phones(userdto.getPhones())
				.createdDt(new Date()).isActive(Boolean.TRUE).lastlogin(new Date())
				.build();
	}


	@Override
	public UserResponseDTO login(String pass) {
		UserModel userfound = repositoryUser.findByPassword(pass).orElseThrow();

		UserDTO userToDto = UserDTO.modelToDto(userfound);
		
		if(!(userfound == null)) {
			
			userfound.setLastLogin(new Date());
			repositoryUser.save(userfound);
			
			return UserResponseDTO.builder()
					.name(userfound.getName()).email(userfound.getEmail())
					.password(userfound.getPassword()).phones(userToDto.getPhones())
					.createdDt(userfound.getCreatedDt()).isActive(Boolean.TRUE).lastlogin(new Date())
					.build();
		}
		return null;
	}

}
