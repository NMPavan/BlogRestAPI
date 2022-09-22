package com.example.BlogApp.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.BlogApp.Entity.Post;
import com.example.BlogApp.Repoistory.PostRepoistory;
import com.example.BlogApp.payload.PostDTO;
import com.example.BlogApp.payload.PostResponse;
import com.example.BlogApp.service.PostService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/posts")
public class PostController {

	@Autowired
	private PostService service;

	private PostRepoistory repo;

	public PostController(PostRepoistory repo) {
		this.repo = repo;
	}

	@PostMapping("/api/v1/post")
	public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDto) {
		return new ResponseEntity<PostDTO>(service.createPost(postDto), HttpStatus.CREATED);
	}

	@GetMapping("api/v1/allPosts")
	public PostResponse getAllPostsData(
			@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "15", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		return service.getAllPostDTO(pageNo, pageSize,sortBy,sortDir);
	}

	@GetMapping("api/v1/post/{id}")
	public PostDTO getPostById(@PathVariable Long id) {
		return service.getPostDTOById(id);
	}

	@PutMapping("api/v1/update/{id}")
	public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postdto, @PathVariable Long id) {
		return new ResponseEntity<PostDTO>(service.updatePostData(id, postdto), HttpStatus.OK);
	}

	@DeleteMapping("api/v1/del/{id}")
	public ResponseEntity<String> deletePost(@PathVariable Long id) {
		service.deletePost(id);
		String message = "Post id:" + id + "deleted successfully";
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

}
