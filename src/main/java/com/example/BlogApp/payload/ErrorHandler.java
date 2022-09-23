package com.example.BlogApp.payload;
import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorHandler {
	private Date timestamp;
	private String message;
	private String details;

}
