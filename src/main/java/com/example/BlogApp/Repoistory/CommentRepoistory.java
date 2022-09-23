package com.example.BlogApp.Repoistory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BlogApp.Entity.CommentEntity;
import com.example.BlogApp.payload.CommentDTO;

public interface CommentRepoistory extends JpaRepository<CommentEntity, Long> {
	
	List<CommentEntity> findByPostData(Long postId);

}
