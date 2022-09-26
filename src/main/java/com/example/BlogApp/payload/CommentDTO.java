package com.example.BlogApp.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class CommentDTO {

	private Long id;

	@NotEmpty
	//@Min(value = 10 ,message = "body must have 10 characters")
	private String body;
	
	@NotEmpty
	//@Min(value = 5,message = "atleast should have 5 charcaters")
	private String name;
	
	@Email
	@NotEmpty(message = "Should have value in email field")
	private String email;
}
