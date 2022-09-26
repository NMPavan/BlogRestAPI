package com.example.BlogApp.Repoistory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.BlogApp.Entity.User;

public interface UserRepoistory extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);

	Optional<User> findByUsernameOrEmail(String username, String email);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

}
