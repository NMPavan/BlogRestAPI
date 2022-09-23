package com.example.BlogApp.serviceImpl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.BlogApp.Entity.CommentEntity;
import com.example.BlogApp.Entity.Post;
import com.example.BlogApp.Exception.BlogApiException;
import com.example.BlogApp.Repoistory.CommentRepoistory;
import com.example.BlogApp.Repoistory.PostRepoistory;
import com.example.BlogApp.payload.CommentDTO;
import com.example.BlogApp.payload.CommentDTO;
import com.example.BlogApp.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	private CommentRepoistory commentRepo;
	private PostRepoistory postRepo;
	private ModelMapper mapper;

	public CommentServiceImpl(CommentRepoistory commentRepo, PostRepoistory postRepo, ModelMapper mapper) {
		this.commentRepo = commentRepo;
		this.postRepo = postRepo;
		this.mapper = mapper;
	}

	@Override
	public CommentDTO saveComment(Long postId, CommentDTO commentDto) {

		CommentEntity comment = mapToEntity(commentDto);

		// retrieve post entity by id
		Post post = postRepo.findById(postId).get();
		// set post to comment entity
		comment.setPost(post);

		// comment entity to DB
		CommentEntity newComment = commentRepo.save(comment);
		return entityToDTO(newComment);
	}

	private CommentDTO entityToDTO(CommentEntity commentObj) {
		CommentDTO commentDTO = mapper.map(commentObj, CommentDTO.class);
		return commentDTO;
	}

	private CommentEntity mapToEntity(CommentDTO commentDTO) {
		CommentEntity comment = mapper.map(commentDTO, CommentEntity.class);
		return comment;

	}

	public List<CommentDTO> getAllCommentsByPostId(Long postId) {

		// retrieve comments by postId
		List<CommentEntity> comments = commentRepo.findByPostData(postId);
		// convert list of comment entities to list of comment dto's
		return comments.stream().map(comment -> entityToDTO(comment)).collect(Collectors.toList());
	}

	@Override
	public CommentDTO getCommentById(Long postId, Long commentId) {
		Post post = postRepo.findById(postId).get();
		CommentEntity comment = commentRepo.findById(commentId).get();
		if (!comment.getPost().getData().equals(post.getData())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");

		}

		return entityToDTO(comment);
	}

	@Override
	public CommentDTO updateComment(Long postId, CommentDTO commentDto, Long commentId) {

		Post post = postRepo.findById(postId).get();
		CommentEntity comment = commentRepo.findById(commentId).get();
		if (!comment.getPost().getData().equals(post.getData())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");

		}
		comment.setBody(commentDto.getBody());
		comment.setEmail(commentDto.getEmail());
		comment.setName(commentDto.getName());
		comment.setId(commentDto.getId());

		CommentEntity updatedComment = commentRepo.save(comment);

		return entityToDTO(updatedComment);
	}

}
