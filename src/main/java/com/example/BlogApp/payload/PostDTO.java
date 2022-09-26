package com.example.BlogApp.payload;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PostDTO {

	
	private long data;
	
	@NotEmpty
	@Size(min = 2,message = "title should contain atleast 2 characters")
	private String title;
	
	@NotEmpty
	@Size(min = 10, message = "description should contain 10 characters")
	private String description;
	
	@NotEmpty
	private String content;
	
	private Set<CommentDTO> commentDto;
}
