package com.blog.restapi.service;

import java.util.List;

import com.blog.restapi.payload.CategoryDto;

public interface CategoryService {

	CategoryDto addCategory(CategoryDto categoryDto);

	CategoryDto getCategoryById(Long id);

	List<CategoryDto> getAllCategories();

	CategoryDto updateCategory(CategoryDto categoryDto, Long id);

	void deleteCategory(Long id);
}
