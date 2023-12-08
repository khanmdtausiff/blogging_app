package com.app.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.blog.config.AppConstants;
import com.app.blog.entities.Role;
import com.app.blog.repo.RoleRepo;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner{
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}
	
	@Bean  //we declared it here because @Bean is always declared in Configuration class.
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println(this.passwordEncoder.encode("mtk"));
		
		try {
			
			Role role1 = new Role();
			role1.setRollId(AppConstants.ADMIN_USER);
			role1.setRollName("ROLE_ADMIN");
			
			Role role2 = new Role();
			role2.setRollId(AppConstants.NORMAL_USER);
			role2.setRollName("ROLE_NORMAL");
			
			List<Role> roles = List.of(role1, role2);
			List<Role> result = this.roleRepo.saveAll(roles);
			result.forEach(r -> System.out.println(r.getRollName()));
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
