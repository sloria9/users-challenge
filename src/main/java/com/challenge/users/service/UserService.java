package com.challenge.users.service;

import com.challenge.users.dto.UserDTO;
import com.challenge.users.dto.UserResponseDTO;

public interface UserService {
	
	UserResponseDTO signUp(UserDTO userdto);
	
	UserResponseDTO login(String pass);
}
