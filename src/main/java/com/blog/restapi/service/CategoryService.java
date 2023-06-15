package com.blog.restapi.service;

import java.util.List;

import com.blog.restapi.payload.response.CategoryResponse;

public interface CategoryService {

	CategoryResponse addCategory(CategoryResponse categoryDto);

	CategoryResponse getCategoryById(Long id);

	List<CategoryResponse> getAllCategories();

	CategoryResponse updateCategory(CategoryResponse categoryDto, Long id);

	void deleteCategory(Long id);
}
