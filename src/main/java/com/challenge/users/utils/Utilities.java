package com.challenge.users.utils;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.challenge.users.model.UserModel;

public class Utilities {

	private static final String SECRET_KEY = "secretKey";
    private static final int EXPIRY_TIME_FOR_ACCESS_TOKEN = 10 * 60 * 1000;
    private static final int EXPIRY_TIME_FOR_REFRESH_TOKEN = 60 * 60 * 1000;
			
    public static String createJwtToken(UserModel userDetail, HttpServletRequest request, boolean isRefreshToken) {
        if (request != null) {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
            if (!isRefreshToken) {
                return JWT.create()
                		.withSubject(userDetail.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRY_TIME_FOR_ACCESS_TOKEN))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", "ROLE_ADMIN")
                        .sign(algorithm);
            } else {
                return JWT.create()
                		.withSubject(userDetail.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRY_TIME_FOR_REFRESH_TOKEN))
                        .withIssuer(request.getRequestURL().toString())
                        .sign(algorithm);
            }
        } else {
            return null;
        }
    }
    
}
