package com.baseutils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

public class JwtUtils {
	

	public String createJwt(String jwtSecretKey) {
		String token = null;
		try {
		    Algorithm algorithm = Algorithm.HMAC256(jwtSecretKey);
		     token = JWT.create()
		        .withIssuer("auth0")
		        .sign(algorithm);
		} catch (JWTCreationException exception){
		    //Invalid Signing configuration / Couldn't convert Claims.
		}
		return token;
	}

}
