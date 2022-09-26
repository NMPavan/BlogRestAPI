package com.example.BlogApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.BlogApp.security.JwtSecurityFilter;
import com.example.BlogApp.security.customDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

//	@Autowired
//	private customDetailsService userDetailsService;
	
	private static final String[] AUTH_WHITELIST = {
	        "/authenticate",
	        "/swagger-resources/**",
	        "/swagger-ui/**",
	        "/v2/api-docs",
	        "/swagger-ui.html",
	        "/webjars/**"
	};

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Bean
	public JwtSecurityFilter jwtAuthenticationFilter() {
		return new JwtSecurityFilter();
	}
	
	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf().disable()
		.exceptionHandling()
		.authenticationEntryPoint(jwtAuthenticationEntryPoint)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authorizeRequests()
		
		
		//pending things with antmatchers
		.antMatchers("/posts/**").permitAll()
		.antMatchers(HttpMethod.POST,"/api/v1/postData/**").permitAll()
		.antMatchers("/api/v1/auth/**").permitAll()
		//.antMatchers("/v3/api-doc/**").permitAll()
		.antMatchers(AUTH_WHITELIST).permitAll()
		.anyRequest()
		.authenticated();
		
		 http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();

	}

	@Bean
	protected AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	
	
	
	

	// creating the users in inmemorydatabase with authroized roles
//	@Bean
//	protected UserDetailsService userDetailsService() {
//		
//		String password1 = passwordEncoder().encode("ram");
//		System.out.println("password1"+ password1);
//		
//		System.out.println();
//		UserDetails user1 =  User.builder().username("pavan").password(password1).roles("ADMIN")
//				.build();
//		UserDetails user2 = User.builder().username("ram").password(passwordEncoder()
//				.encode("pavan")).roles("EMP")
//				.build();
//		
//		UserDetails user3 = User.builder().username("trap").password(passwordEncoder()
//				.encode("fall")).roles("USER")
//				.build();
//	
//
//		return new InMemoryUserDetailsManager(user1,user2,user3);
//	}

}
