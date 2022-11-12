package com.challenge.users.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.challenge.users.dto.ErrorsDTO;
import com.challenge.users.dto.ExceptionDTO;
import com.challenge.users.dto.UserResponseDTO;
import com.challenge.users.model.UserModel;
import com.challenge.users.repository.UserRepository;
import com.challenge.users.utils.Utilities;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private final AuthenticationManager authenticationManager;

	private final UserRepository userRepository;

	/**
	 * Método para realizar la autenticación del User. Se toma el JSON enviado por
	 * el cliente y lo mapea a la entidad User, se busca en el repositorio y en caso
	 * de no existir se tira la excepción UserNotFoundException, y en caso de ser
	 * encontrado, se prosigue con la autenticación
	 *
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @return Authentication
	 * @throws AuthenticationException Excepción lanzada por Spring Security.
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			String email = request.getParameter("email");
			String pass = request.getParameter("password");			
			UserModel userFromDb = userRepository.findByEmail(email);
			if (userFromDb != null && userFromDb.getIsActive()) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, pass);
				return authenticationManager.authenticate(authToken);
			}else {
				throw new UsernameNotFoundException("User with email " + email + " not found");
			}
		}catch (AuthenticationException e) {
			ErrorsDTO errors = new ErrorsDTO(new ArrayList<>());
			ExceptionDTO errorDetails = new ExceptionDTO(new Date(), HttpStatus.FORBIDDEN.value(), e.getMessage());
			errors.addException(errorDetails);
			response.setStatus(HttpStatus.FORBIDDEN.value());
			response.setContentType("application/json");
			try {
				new ObjectMapper().writeValue(response.getOutputStream(), errors);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Este método se ejecuta si la autenticación es exitosa.
	 *
	 * <p>Se recupera el usuario desde Spring Security y se recuperan todos los datos
	 * desde la base de datos, luego se generan los token JWT para acceso y
	 * refresco, y actualiza la fecha de último login del usuario para finalmente
	 * devolver el objeto como JSON
	 *
	 * @param request        HttpServletRequest
	 * @param response       HttpServletResponse
	 * @param chain          FilterChain
	 * @param authentication Authentication
	 * @throws IOException      Excepción lanzada por Spring Security
	 * @throws ServletException Excepción lanzada por Spring Security.
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		User userDetail = (User) authResult.getPrincipal();
		UserModel user = userRepository.findByEmail(userDetail.getUsername());
		String accessToken = Utilities.createJwtToken(user, request, false);
		String refreshToken = Utilities.createJwtToken(user, request, true);
		user.setLastLogin(new Date());
		UserResponseDTO userUpdated = UserResponseDTO.entityToDTO(userRepository.save(user));
		userUpdated.setToken(accessToken);

		Map<String, String> tokens = new HashMap<>();
		tokens.put("accessToken", accessToken);
		tokens.put("refreshToken", refreshToken);
		response.setContentType("application/json");
		new ObjectMapper().writeValue(response.getOutputStream(), userUpdated);

	}


}
