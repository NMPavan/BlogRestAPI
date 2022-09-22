package com.example.BlogApp.Repoistory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BlogApp.Entity.Post;

public interface PostRepoistory extends JpaRepository<Post, Long> {

}
