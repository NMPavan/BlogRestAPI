package com.example.BlogApp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
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
@RequestMapping("/postData")
public class CommentController {
	
	private CommentService service;
	
	public CommentController(CommentService service) {
		//super();
		this.service = service;
	}



    @PostMapping("api/v1/{id}/comments")
	public ResponseEntity<CommentDTO> createComment(@PathVariable long id,@RequestBody CommentDTO comment){
    	return ResponseEntity.ok(service.saveComment(id, comment));
	}
    
    @GetMapping("posts/{postId}/comments")
    public List<CommentDTO> getCommentsByPostId(@PathVariable(value = "postId") Long postId){
        return service.getAllCommentsByPostId(postId);
    }
    
    @GetMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable long postId,@PathVariable long commentId){
    	
    	return ResponseEntity.ok(service.getCommentById(postId,commentId));
    }
    
    
    @PutMapping("api/v1/{id}/comments/{commentId}")
   	public ResponseEntity<CommentDTO> UpdateComment(@PathVariable long id,@RequestBody CommentDTO comment,@PathVariable long commentId){
       	return ResponseEntity.ok(service.updateComment(id, comment,commentId));
   	}
	
}
