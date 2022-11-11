package com.challenge.users.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.users.dto.UserDTO;
import com.challenge.users.dto.UserResponseDTO;
import com.challenge.users.service.UserService;

@RestController
@RequestMapping("/challenge/v1")
public class UsersController {
	
	@Autowired
	private UserService userService;
	
    @PostMapping("/sign_up")
	public ResponseEntity<UserResponseDTO> signup(@Valid @RequestBody UserDTO userdto) {
		return new ResponseEntity<UserResponseDTO>(userService.signUp(userdto), HttpStatus.CREATED);
	}
}
