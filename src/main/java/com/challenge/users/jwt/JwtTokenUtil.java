package com.challenge.users.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import com.challenge.users.model.UserModel;

@Component
public class JwtTokenUtil {

	private static final long EXPIRE_DURATION = 24*60*60*1000;

	@Value("${app.jwt.secret}")
	private String secretKey;

	public String generateAccessToken(UserModel user) {
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + EXPIRE_DURATION);

		return Jwts.builder().setSubject(user.getEmail())
				.setIssuedAt(new Date())
				.setExpiration(expireDate)
				.signWith(SignatureAlgorithm.HS512, secretKey)
				.compact();
	}
}
