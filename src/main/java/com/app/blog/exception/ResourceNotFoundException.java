package com.app.blog.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter  
public class ResourceNotFoundException extends RuntimeException { //need to extend with unchecked Exception i.e. RuntimeException...if we use extends Exception then it will throw error in UserServiceImpl class at L34.
	
	private String resourceName;
	private String fieldName;
	private long fieldValue;
	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
		super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));  //very important line
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	

}
