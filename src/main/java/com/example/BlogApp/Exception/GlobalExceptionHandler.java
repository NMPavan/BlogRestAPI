package com.example.BlogApp.Exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.example.BlogApp.payload.ErrorHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(PostNotFoundException.class)
	public ResponseEntity<ErrorHandler> handlerComments(PostNotFoundException post, WebRequest web) {
		ErrorHandler error = new ErrorHandler(new Date(System.currentTimeMillis()), post.getMessage(),
				web.getDescription(false));
		return new ResponseEntity<ErrorHandler>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BlogApiException.class)
	public ResponseEntity<ErrorHandler> handlerBlogApi(BlogApiException post, WebRequest web) {
		ErrorHandler error = new ErrorHandler(new Date(System.currentTimeMillis()), post.getMessage(),
				web.getDescription(false));
		return new ResponseEntity<ErrorHandler>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorHandler> globalExceptionHandler(Exception post, WebRequest web) {
		ErrorHandler error = new ErrorHandler(new Date(System.currentTimeMillis()), post.getMessage(),
				web.getDescription(false));
		return new ResponseEntity<ErrorHandler>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
