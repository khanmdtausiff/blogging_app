package com.app.blog.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.blog.entities.Category;
import com.app.blog.entities.Post;
import com.app.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	
	Page<Post> findByUser(User user, Pageable pageable); //Custom finder methods..Here User user we passed instead of Integer userId because in Post we've declared User user & category category!!
	
	Page<Post> findByCategory(Category category, Pageable pageable);
	
	List<Post> findBypostTitleContaining(String postTitle); //need to write function name in this manner only ..need to write findBypostTitleContaining instead of findByTitleContaining..then only method will execute...here postTitle is field name.

}
