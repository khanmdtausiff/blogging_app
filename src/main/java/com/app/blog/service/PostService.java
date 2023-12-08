package com.app.blog.service;

import java.util.List;

import com.app.blog.payload.PostDto;
import com.app.blog.payload.PostResponse;

public interface PostService {
	
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	PostDto updatePost(PostDto postDto, Integer postId);
	
	PostDto getPostById(Integer postId);
	
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	void deletePost(Integer postId);
	
	PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize);
	
	PostResponse getPostsByUser(Integer userId, Integer pageNumber, Integer pageSize);
	
	List<PostDto> searchPosts(String keyword);
	
	

}
