package com.app.blog.customvalidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserNameValidator implements ConstraintValidator<UserNameValid, String>{
	
	private Logger logger = LoggerFactory.getLogger(UserNameValidator.class);
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		logger.info("Message from isValid is : {} " + value);
		
		//logic
		if(value.isEmpty())
		{
			return false;
		}
		else {
			return true;
			
		}
		
	}

}
