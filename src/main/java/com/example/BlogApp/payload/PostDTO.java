package com.example.BlogApp.payload;

import lombok.Data;

@Data
public class PostDTO {

	private long data;
	private String title;
	private String description;
	private String content;
}
