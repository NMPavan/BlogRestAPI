package com.example.BlogApp.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.BlogApp.Entity.Role;
import com.example.BlogApp.Entity.User;
import com.example.BlogApp.Repoistory.UserRepoistory;

@Service
public class customDetailsService implements UserDetailsService {

	private UserRepoistory userRepository;

    public customDetailsService(UserRepoistory userRepository) {
        this.userRepository = userRepository;
    }

	@Override
	public UserDetails loadUserByUsername(String usernameorEmail) throws UsernameNotFoundException {
		User user = userRepository.findByUsernameOrEmail(usernameorEmail, usernameorEmail)
				.orElseThrow(() -> new UsernameNotFoundException("Username or email is not found" + usernameorEmail));
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				mapToSimpleGrantedAuthority(user.getRole())

		);
	}

	private Collection<? extends GrantedAuthority> mapToSimpleGrantedAuthority(Set<Role> roles) {

		return roles.stream().map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

	}

}
