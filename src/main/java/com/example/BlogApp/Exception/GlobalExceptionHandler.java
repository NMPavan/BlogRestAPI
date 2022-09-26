package com.example.BlogApp.Exception;

import java.io.ObjectInputStream.GetField;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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

//    @Override
//	protected ResponseEntity<Object> handleMethodArgumentNotValidExc(MethodArgumentNotValidException ex,
//			HttpHeaders headers, HttpStatus status, WebRequest request) {
//
//		Map<String, String> errors = new HashMap<>();
//
//		ex.getBindingResult().getAllErrors().forEach((error) -> {
//
//			String fieldName = ((FieldError) error).getField();
//			String message = error.getDefaultMessage();
//			errors.put(fieldName, message);
//		});
//		System.out.println("errors="+errors);
//
//		return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
//	}

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception,
                                                                      WebRequest webRequest){
      Map<String, String> errors = new HashMap<>();
      exception.getBindingResult().getAllErrors().forEach((error) ->{
          String fieldName = ((FieldError)error).getField();
          String message = error.getDefaultMessage();
          errors.put(fieldName, message);
      });
      return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }
}
