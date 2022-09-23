package com.example.BlogApp.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PostNotFoundException extends RuntimeException {

	private String resourceName;
	private String fieldName;
	private String fieldValue;

	public PostNotFoundException(String resourceName, String fieldName, String postId) {
		super(String.format("%s not found with %s : '%s'", resourceName, fieldName, postId));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = postId;
	}



	public String getResourceName() {
		return resourceName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}
}
