package com.app.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.blog.config.AppConstants;
import com.app.blog.entities.Role;
import com.app.blog.entities.User;
import com.app.blog.exception.ResourceNotFoundException;
import com.app.blog.payload.UserDto;
import com.app.blog.repo.RoleRepo;
import com.app.blog.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user = this.dtoUser(userDto);
		User savedUser = this.userRepo.save(user); //since it will take user as parameter so we need to setUserDto to User..so we have made 2 methods below to set.
		UserDto userDto1 = this.userToDto(savedUser);
		return userDto1;
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user1 = this.userRepo.findById(userId).
				orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
		user1.setPassword(userDto.getPassword());
		user1.setName(userDto.getName());
		user1.setEmail(userDto.getEmail());
		user1.setAbout(userDto.getAbout());
		User updatedUser = this.userRepo.save(user1);
		
		UserDto userDto2 = this.userToDto(updatedUser);
		return userDto2;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		
		User user2 = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
		UserDto userDto3 = this.userToDto(user2);
		return userDto3;
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user3 = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
		this.userRepo.delete(user3);
	}

	
	public User dtoUser(UserDto userDto) 
	{
		User user = this.modelMapper.map(userDto, User.class);
		return user;
	}
	
	public UserDto userToDto(User user) 
	{
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}

	@Override
	public UserDto registerUser(UserDto userDto) {
		
		User user = this.modelMapper.map(userDto, User.class);
		
		//encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		//roles
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();  //we've assumed here that if a new user is getting registered he'll be a Normal user
		user.getRoles().add(role);
		
		User registeredUser = this.userRepo.save(user);
		return this.modelMapper.map(registeredUser, UserDto.class);
		
	}

}
