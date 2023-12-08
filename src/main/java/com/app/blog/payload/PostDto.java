package com.app.blog.payload;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.app.blog.entities.Comment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto 
{
	
	private int postId;
	
	@NotEmpty
	@Size(min = 2, message = "Post Title should exceed more than 2 characters!!")
	private String postTitle;
	
	@NotEmpty
	@Size(min = 4, message = "Post content should exceed more than 4 characters!!")
	private String postContent;
	
	private String imageName;
	
	private Date date;
	
	private CategoryDto category; //Also very important to note that variable names such as category & user should be used...because we've to keep the same name that we declared in Post entity for mapping to work..so even if we've used CategoryDto & PostDto here but the variable names are category & user.
	
    private UserDto user; //Here we've used UserDto & CategoryDto instead of User & category because to avoid infinite recursion when we create a Post.In CategoryDto & UserDto we don't have any field as Post..so infinite recursion wouldn't happen!
	
    private Set<CommentDto> comments = new HashSet<>();
	
	

}
