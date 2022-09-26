package com.example.BlogApp.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BlogApp.Entity.Role;
import com.example.BlogApp.Entity.User;
import com.example.BlogApp.Repoistory.RoleRepoistory;
import com.example.BlogApp.Repoistory.UserRepoistory;
import com.example.BlogApp.payload.JwtAuthResponse;
import com.example.BlogApp.payload.LoginDto;
import com.example.BlogApp.payload.SignupDto;
import com.example.BlogApp.security.JwtTokenProvider;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepoistory repo;
	
	
	@Autowired
	private RoleRepoistory roleRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@PostMapping("/signin")
	public ResponseEntity<JwtAuthResponse> authenticateUser(@RequestBody LoginDto logindto){
		
//	    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                logindto.getUsernameOrEmail(), logindto.getPassword()));
//	    
//	    SecurityContextHolder.getContext().setAuthentication(authentication);
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                logindto.getUsernameOrEmail(), logindto.getPassword()));
		
		//System.out.println("authentication"+ authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.JwtTokenProviderData(authentication);
        
	    return ResponseEntity.ok(new JwtAuthResponse(token, "Bearer"));
	    
	}
	
	
	@PostMapping("/signup")
	public ResponseEntity<String> signupUser(@RequestBody SignupDto signupDto){
		
		//username already taken
		if(repo.existsByUsername(signupDto.getUsername())) {
			return new ResponseEntity<String>("Username already taken",HttpStatus.BAD_REQUEST);
		}
		
		//email check
		if(repo.existsByEmail(signupDto.getEmail())) {
			return new ResponseEntity<String>("Email is already in use",HttpStatus.BAD_REQUEST);
		}
		
		User user = new User();
		
		user.setUsername(signupDto.getUsername());
		user.setName(signupDto.getName());
		user.setEmail(signupDto.getEmail());
		user.setPassword(passwordEncoder.encode(signupDto.getPassword()));
		
		Role roles = roleRepo.findByName("ROLE_USER").get();
        user.setRole(Collections.singleton(roles));

        repo.save(user);
        
        return new ResponseEntity<String>("User signedup successfully",HttpStatus.OK);
		
	}
	

}
