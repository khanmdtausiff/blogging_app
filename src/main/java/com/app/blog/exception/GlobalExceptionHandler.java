package com.app.blog.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.app.blog.payload.ApiResponse;

@RestControllerAdvice //This we use when we declare a centralized exception handler class
public class GlobalExceptionHandler { //This handler will handle all the exceptions that will be coming from @Controller

	@ExceptionHandler(ResourceNotFoundException.class) //This annotation to be used in Handler method for centralized exception
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex)
	{
		String msg = ex.getMessage();
		return new ResponseEntity<>(new ApiResponse(msg, false), HttpStatus.NOT_FOUND); //we kept it as false because if user is not found then success will show as false!
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class) //this will handle exceptions for validations that we're getting in @Controller
	public ResponseEntity<Map<String, String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex1)
	{
		Map<String, String> response = new HashMap<>();
		ex1.getBindingResult().getAllErrors().forEach(error->{
			String field = ((FieldError) error).getField();  //getField() is provided to FieldError..so we need to typecast it to use with error..because error is object error and field error is class error...and FieldError extends ObjectError! 
			String message = error.getDefaultMessage();
			response.put(field, message);
		});
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> apiExceptionHandler(ApiException ex)
	{
		String msg1 = ex.getMessage();
		return new ResponseEntity<>(new ApiResponse(msg1, false), HttpStatus.BAD_REQUEST); //we kept it as false because if user is not found then success will show as false!
	}
	
	
	
	
	
}
