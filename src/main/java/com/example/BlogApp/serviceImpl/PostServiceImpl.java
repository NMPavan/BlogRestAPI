package com.example.BlogApp.serviceImpl;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BlogApp.Entity.Post;
import com.example.BlogApp.Exception.PostNotFoundException;
import com.example.BlogApp.Repoistory.PostRepoistory;
import com.example.BlogApp.payload.PostDTO;
import com.example.BlogApp.payload.PostResponse;
import com.example.BlogApp.service.PostService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Sort;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

	private PostRepoistory repo;

	public PostServiceImpl(PostRepoistory repo) {
		this.repo = repo;
	}

	// private ModelMapper mapper;

	@Override
	public PostDTO createPost(PostDTO postDto) {
		// convert the postDTO to Post
		Post post = mapToEntity(postDto);

		Post newPost = repo.save(post);

		// convery entity to dto
		PostDTO postDTOObj = entityToDTO(newPost);

		return postDTOObj;
	}

	private PostDTO entityToDTO(Post newPost) {
		// PostDTO postDto = mapper.map(post, PostDTO.class);
		PostDTO postDTOObj = new PostDTO();
		postDTOObj.setId(newPost.getId());
		postDTOObj.setTitle(newPost.getTitle());
		postDTOObj.setDescription(newPost.getDescription());
		postDTOObj.setContent(newPost.getContent());
		return postDTOObj;
	}

	private Post mapToEntity(PostDTO postDto) {

		// Post post = mapper.map(postDto, Post.class);

		Post post = new Post();
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());

		return post;

	}

	public PostResponse getAllPostDTO(int pageNo, int pageSize,String sortBy,String sortDir) {
//		Pageable pageableOne = (Pageable) PageRequest.of(pageNo, pageSize);
//		Page<Post> posts = repo.findAll(pageableOne);
		// , String sortBy, String sortDir
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();

//		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
//				: Sort.by(sortBy).descending();

		PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);

		Page<Post> posts = repo.findAll(pageable);
		System.out.println("posts=" + posts);
		List<Post> listPosts = posts.getContent();

		List<PostDTO> postDto = listPosts.stream().map(post -> entityToDTO(post)).collect(Collectors.toList());

		PostResponse pos = new PostResponse();
		pos.setContent(postDto);
		pos.setPageNo(posts.getNumber());
		pos.setPageSize(posts.getSize());
		pos.setLast(posts.isLast());
		pos.setTotalElements(posts.getTotalElements());
		pos.setTotalPages(posts.getTotalPages());

		return pos;
	}

	@Override
	public PostDTO getPostDTOById(Long id) {
		Post post = repo.findById(id).orElseThrow(() -> new PostNotFoundException("Post", "Id", String.valueOf(id)));
		PostDTO postDto = entityToDTO(post);
		return postDto;
	}

	@Override
	public PostDTO updatePostData(Long id, PostDTO postDto) {
		Post post = repo.findById(id).get();

		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());

		Post postData = repo.save(post);
		return entityToDTO(postData);

	}

	@Override
	public void deletePost(Long id) {
		repo.deleteById(id);

	}

//	@Override
//	public List<PostDTO> getAllPostDTO(int pageNo, int pageSize) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
