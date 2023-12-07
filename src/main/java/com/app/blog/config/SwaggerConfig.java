package com.app.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
	
	@Bean
	public OpenAPI openApi()
	{
		String schemeName = "bearerScheme";
		
		return new OpenAPI()
				  
				  .addSecurityItem(new SecurityRequirement()
						  			.addList(schemeName)
						  	    	)
				  .components(new Components()
						  	.addSecuritySchemes(schemeName, new SecurityScheme()
						  									.name(schemeName)
						  									.type(SecurityScheme.Type.HTTP)
						  									.bearerFormat("JWT")
						  									.scheme("bearer")
						  						)
						  	)	
				
				  .info(new Info().title("Blogging Application using Spring Boot")
	              .description("This project is developed by Md Tausiff Khan!")
	              .version("v0.0.1")
	              .contact(new Contact().name("Tausiff").email("khanmdtausiff@gmail.com"))
	              .license(new License().name("License of APIs").url("API License URL")))
	              .externalDocs(new ExternalDocumentation()
	              .description("Click here to access my GitHub Link!!")
	              .url("https://github.com/khanmdtausiff"));
	}

}
