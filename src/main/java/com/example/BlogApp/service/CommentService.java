package com.example.BlogApp.service;

import java.util.List;

import com.example.BlogApp.Entity.CommentEntity;
import com.example.BlogApp.payload.CommentDTO;

public interface CommentService {

	CommentDTO saveComment(Long postId,CommentDTO commentDto);
	List<CommentDTO> getAllCommentsByPostId(Long postId);
	CommentDTO getCommentById(Long postId,Long commentId);
	
	CommentDTO updateComment(Long postId,CommentDTO comment,Long commentId);
}
