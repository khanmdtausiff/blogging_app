package com.app.blog.service;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.blog.entities.Category;
import com.app.blog.exception.ResourceNotFoundException;
import com.app.blog.payload.CategoryDto;
import com.app.blog.repo.CategoryRepo;

import jakarta.transaction.Transactional;

@Service
//@Transactional  //very important...have to use this annotation as when we hit PUT api...everything works fine data shows updated in postman but in database..it doesn't gets updated..so have to use @Transactional for this issue.
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {

		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category createdCategory = this.categoryRepo.save(category);
		return this.modelMapper.map(createdCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category category1 = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
		category1.setCategoryTitle(categoryDto.getCategoryTitle());
		category1.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updatedCat = this.categoryRepo.save(category1);
		return this.modelMapper.map(updatedCat, CategoryDto.class);
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		Category category2 = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
		return this.modelMapper.map(category2, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> categoryList = this.categoryRepo.findAll();
		List<CategoryDto> categoryDtoList = categoryList.stream().map(category-> this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		return categoryDtoList;
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category category3 = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		this.categoryRepo.delete(category3);
	}

}
