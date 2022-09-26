package com.example.BlogApp.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;

@Component
public class JwtSecurityFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private customDetailsService customDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// read token from Auth head
		String token = getJWTfromRequest(request);

		  if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)){
			// do validation to token and get username from it
			String username = jwtTokenProvider.getUserNameFromToken(token);
			//load user name with token
			UserDetails userDetails = customDetailsService.loadUserByUsername(username);
			
			UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());
			
			//doubt need to clarify
			user.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			//simply creating and storing in a session based 
			SecurityContextHolder.getContext().setAuthentication(user);

		}
		  //iterator for next filter to be invoked
		  filterChain.doFilter(request, response);

	}

	// Bearer <accessToken>
	private String getJWTfromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}

}
