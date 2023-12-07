package com.app.blog.payload;

import java.util.HashSet;
import java.util.Set;

import com.app.blog.customvalidator.UserNameValid;
import com.app.blog.entities.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {  //Using this UserDto to expose rather than using User entity..we'll be having validations here.
	
	private int userId;
	
	@UserNameValid
	@Size(min = 4, message = "User name must be min of 4 characters!! " )
	private String name;
	
	@Email(message = "Email is not valid!!")
	private String email;
	
	@NotEmpty
	@Size(min = 3, max = 10, message = "Password must be between 3-10 characters!!")
	private String password;
	
	@NotEmpty
	private String about;
	
	private Set<CommentDto> comments = new HashSet<>();
	
	private Set<RoleDto> roles = new HashSet<>();

}
