package com.app.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {
	
	private int commentId;
	
	@NotEmpty(message = "Comment can't be empty!!")
	private String commentContent;
	


}
