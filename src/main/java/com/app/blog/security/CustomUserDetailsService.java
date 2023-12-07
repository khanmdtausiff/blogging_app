package com.app.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.blog.entities.User;
import com.app.blog.exception.ResourceNotFoundException;
import com.app.blog.repo.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService{ //for authentication with database.
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User", "email", Long.parseLong(username)));
		return user;
	}

}
