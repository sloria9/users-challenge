package com.challenge.users.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.challenge.users.dto.UserDTO;
import com.challenge.users.dto.UserResponseDTO;
import com.challenge.users.model.UserModel;
import com.challenge.users.repository.UserRepository;
import com.challenge.users.service.UserService;
import com.challenge.users.utils.Utilities;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService{

	private final UserRepository repositoryUser;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserModel user = repositoryUser.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ADMIN"));
		return new User(user.getEmail(), user.getPassword(), authorities);
	}

	@Override
	public UserResponseDTO signUp(UserDTO userdto) {

		String encrypPass = passwordEncoder.encode(userdto.getPassword());
		userdto.setPassword(encrypPass);
		
		UserModel userNew = UserModel.dtoToModel(userdto);
		UserModel savedUser = repositoryUser.save(userNew);
		String token = getTokenForUser(savedUser);
		
		UserResponseDTO userReturn = UserResponseDTO.builder().id(savedUser.getId())
				.name(userdto.getName()).email(userdto.getEmail())
				.password(userdto.getPassword()).phones(userdto.getPhones())
				.createdDt(new Date()).isActive(Boolean.TRUE).lastlogin(new Date())
				.build();
		
		userReturn.setToken(token);
		
		return userReturn;
	}


    /**
     * Metodo para agregar token JWT al usuario.
     *
     * @param UserEntity user
     * @return String
     */
    public String getTokenForUser(UserModel user) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes != null
                ? ((ServletRequestAttributes) requestAttributes).getRequest()
                : null;
        return Utilities.createJwtToken(user, request, false);
    }

}
