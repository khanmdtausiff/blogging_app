package com.app.blog.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.blog.config.AppConstants;
import com.app.blog.payload.ApiResponse;
import com.app.blog.payload.PostDto;
import com.app.blog.payload.PostResponse;
import com.app.blog.service.FileService;
import com.app.blog.service.PostService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, 
											@PathVariable("userId") Integer userId, 
										@PathVariable("categoryId") Integer categoryId)
	{
		PostDto createPostDto = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPostDto, HttpStatus.CREATED);
	}
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<PostResponse> getPostsByCategory(@PathVariable("categoryId") Integer categoryId, @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber, 
			 @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize)
	{
		PostResponse postResponse = this.postService.getPostsByCategory(categoryId, pageNumber, pageSize);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<PostResponse> getPostsByUser(@PathVariable("userId") Integer userId, @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber, 
			 @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize)
	{
		PostResponse postResponse = this.postService.getPostsByUser(userId, pageNumber, pageSize);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber, 
													@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize, 
													@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy, 
													@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) //required = false means that we can change defaultValue of both pageNumber & pageSize if we want or can ignore...if we don't change then by default values will be what we've set...Also pageNumber starts from zero...also defaultValue = false means we can pass it in our url or we can ignore..depends on us!!
	{
		PostResponse postResponse = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getSinglePost(@PathVariable("postId") Integer postId)
	{
		PostDto postDto = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
	}
	
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Integer postId)
	{
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post deleted successfully", true), HttpStatus.OK);
	}
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable("postId") Integer postId)
	{
		PostDto updatePostDto = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePostDto, HttpStatus.OK);
	}
	
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPosts(@PathVariable("keyword") String keyword)
	{
		List<PostDto> postDtos = this.postService.searchPosts(keyword);
		return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);
	}
	
	@PostMapping("/posts/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadFile(@RequestParam("image") MultipartFile multipartFile, 
											  @PathVariable("postId") Integer postId)
	{
		try {
			
			if(multipartFile.isEmpty())
			{
				return ResponseEntity.internalServerError().build();
			}
			
			String originalFilename = multipartFile.getOriginalFilename();
			if(!originalFilename.toLowerCase().endsWith(".png"))
			{
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
			
			PostDto postDto = this.postService.getPostById(postId); //this line should be on top because if we try to post an image on a post that doesn't exist..then it will throw exception and the code will not move forward!
			String uploadedFile = this.fileService.uploadFile(path, multipartFile);
			postDto.setImageName(uploadedFile);
			PostDto updatedPostDto = this.postService.updatePost(postDto, postId);
			return new ResponseEntity<PostDto>(updatedPostDto, HttpStatus.CREATED);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
}
	
	@GetMapping(value = "/posts/image/{imageName}", produces = MediaType.IMAGE_PNG_VALUE)
	public void downloadFile(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException 
	{
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_PNG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
	
	
	

}
