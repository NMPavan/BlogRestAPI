package com.example.BlogApp.Repoistory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.BlogApp.Entity.CommentEntity;
import com.example.BlogApp.payload.CommentDTO;

public interface CommentRepoistory extends JpaRepository<CommentEntity, Long> {
	
	@Query("select comment from CommentEntity comment where post_id = :postId")
	List<CommentEntity> findByPostId(Long postId);

}
