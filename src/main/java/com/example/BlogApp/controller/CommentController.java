package com.example.BlogApp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BlogApp.Repoistory.CommentRepoistory;
import com.example.BlogApp.payload.CommentDTO;
import com.example.BlogApp.service.CommentService;

@RestController
@RequestMapping("api/v1/postData")
public class CommentController {
	
	private CommentService service;
	
	public CommentController(CommentService service) {
		//super();
		this.service = service;
	}


	@PreAuthorize("hasRole('USER')")
    @PostMapping("api/{id}/comments")
	public ResponseEntity<CommentDTO> createComment(@PathVariable long id,@Valid @RequestBody CommentDTO comment){
    	return ResponseEntity.ok(service.saveComment(id, comment));
	}
    
	@PreAuthorize("hasRole('USER')")
    @GetMapping("posts/{postId}/comments")
    public List<CommentDTO> getCommentsByPostId(@PathVariable(value = "postId") Long postId){
        return service.getAllCommentsByPostId(postId);
    }
    
	@PreAuthorize("hasRole('USER')")
    @GetMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable long postId,@PathVariable long commentId){
    	
    	return ResponseEntity.ok(service.getCommentById(postId,commentId));
    }
    
    
	@PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}/comments/{commentId}")
   	public ResponseEntity<CommentDTO> UpdateComment(@PathVariable long id,@Valid @RequestBody CommentDTO comment,@PathVariable long commentId){
       	return ResponseEntity.ok(service.updateComment(id, comment,commentId));
   	}
	
}
