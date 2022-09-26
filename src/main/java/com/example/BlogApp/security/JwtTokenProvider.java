package com.example.BlogApp.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.BlogApp.Exception.BlogApiException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

	@Value("${app.secret}")
	private String jwtSecretKey;

	@Value("${app.jwt-expiration-milliseconds}")
	private int jwtExpiration;

	public String JwtTokenProviderData(Authentication authentication) {
		String username = authentication.getName();
		Date currentDate = new Date();
		Date expirationDate = new Date(currentDate.getTime() + jwtExpiration);

		String token = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS256, jwtSecretKey).compact();

		return token;
	}

	public String getUserNameFromToken(String token) {
		Claims claims = (Claims) Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token);
			return true;
		} catch (SignatureException ex) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.");
		}
	}
}
