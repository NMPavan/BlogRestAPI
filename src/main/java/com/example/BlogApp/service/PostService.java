package com.example.BlogApp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.BlogApp.Entity.Post;
import com.example.BlogApp.payload.PostDTO;
import com.example.BlogApp.payload.PostResponse;


public interface PostService {

	PostDTO createPost(PostDTO postDto);
	
	PostResponse getAllPostDTO(int pageNo,int pageSize,String sortBy,String sortDir);
	
	PostDTO getPostDTOById(Long id);
	
	PostDTO updatePostData(Long id,PostDTO postDto);
	
	void deletePost(Long id);
}
