package com.example.BlogApp.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.example.BlogApp.utils.AppConstants;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1/posts")
public class PostController {

	@Autowired
	private PostService service;

	private PostRepoistory repo;

	public PostController(PostRepoistory repo) {
		this.repo = repo;
	}

	@PreAuthorize("hasRole('USER')")
	@PostMapping("/post")
	public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDto) {
		return new ResponseEntity<PostDTO>(service.createPost(postDto), HttpStatus.CREATED);
	}

	@GetMapping("/allPosts")
	public PostResponse getAllPostsData(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_BY_DIR, required = false) String sortDir) {
		return service.getAllPostDTO(pageNo, pageSize,sortBy,sortDir);
	}

	@GetMapping("/{id}")
	public PostDTO getPostById(@PathVariable Long id) {
		return service.getPostDTOById(id);
	}

	
	@PreAuthorize("hasRole('USER')")
	@PutMapping("update/{id}")
	public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postdto, @PathVariable Long id) {
		return new ResponseEntity<PostDTO>(service.updatePostData(id, postdto), HttpStatus.OK);
	}

//	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("del/{id}")
	public ResponseEntity<String> deletePost(@PathVariable Long id) {
		service.deletePost(id);
		String message = "Post id:" + id + "deleted successfully";
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

}
