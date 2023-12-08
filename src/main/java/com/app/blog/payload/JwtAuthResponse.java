package com.app.blog.payload;

import lombok.Data;

@Data    //This means @Getter + @Setter
public class JwtAuthResponse {
	
	private String token;

}
