package com.example.BlogApp.Repoistory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BlogApp.Entity.Role;


public interface RoleRepoistory extends JpaRepository<Role, Long> {
	
	Optional<Role> findByName(String name);

}
