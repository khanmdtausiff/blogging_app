package com.app.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
	
	private int categoryId;
	
	@NotEmpty
	@Size(min = 3, message = "Title should be more than 3 characters!!")
	private String categoryTitle;
	
	@NotEmpty
	@Size(min = 5, message = "Description should exceed more than 5 characters!!")
	private String categoryDescription;

}
