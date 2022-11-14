package com.challenge.users.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.challenge.users.dto.ErrorsDTO;
import com.challenge.users.dto.ExceptionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomAuthorizationFilter extends OncePerRequestFilter{

	private static final String SECRET_KEY = "secretKey";


	/**
	 * verify if the user have access
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(request.getServletPath().equals("/challenge/v1/sign_up") || request.getServletPath().equals("/h2-console")) {
			filterChain.doFilter(request, response);
		}else {
			//si es otro path reviso si tiene autorization
			String authorizatinHeader = request.getHeader("Authorization");
			String prefix = "Bearer ";
			if(authorizatinHeader != null && authorizatinHeader.startsWith(prefix)) {
				try{
					String token = authorizatinHeader.substring(prefix.length());
					Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
					JWTVerifier verifier = JWT.require(algorithm).build();
					DecodedJWT decoderJWT = verifier.verify(token);
					String username = decoderJWT.getSubject();
					Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
					authorities.add(new SimpleGrantedAuthority(decoderJWT.getClaim("roles").toString()));
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					filterChain.doFilter(request, response);
				}catch (Exception e) {
					ErrorsDTO errors = new ErrorsDTO(new ArrayList<>());
					ExceptionDTO errorDetails = new ExceptionDTO();
					errorDetails.setCode(HttpStatus.FORBIDDEN.value());
					errorDetails.setDetails(e.getMessage());
					errorDetails.setTimestamp(new Date());
					errors.addException(errorDetails);
					response.setStatus(HttpStatus.FORBIDDEN.value());
					response.setContentType("application/json");
					new ObjectMapper().writeValue(response.getOutputStream(), errors);
				}
			}else {
				ErrorsDTO errors = new ErrorsDTO(new ArrayList<>());
				ExceptionDTO errorDetails = new ExceptionDTO();
				errorDetails.setCode(HttpStatus.FORBIDDEN.value());
				errorDetails.setDetails(HttpStatus.FORBIDDEN.getReasonPhrase());
				errorDetails.setTimestamp(new Date());
				errors.addException(errorDetails);
				response.setStatus(HttpStatus.FORBIDDEN.value());
				response.setContentType("application/json");
				new ObjectMapper().writeValue(response.getOutputStream(), errors);
			}
		}
	}
	//	}

}
