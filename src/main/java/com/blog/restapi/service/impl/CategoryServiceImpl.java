package com.blog.restapi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.blog.restapi.entity.Category;
import com.blog.restapi.exception.ResourceNotFoundException;
import com.blog.restapi.payload.response.CategoryResponse;
import com.blog.restapi.repository.CategoryRepository;
import com.blog.restapi.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	private CategoryRepository categoryRepository;
	private ModelMapper modelMapper;

	public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
		super();
		this.categoryRepository = categoryRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public CategoryResponse addCategory(CategoryResponse categoryDto) {
		Category category = modelMapper.map(categoryDto, Category.class);
		Category savedCategory = categoryRepository.save(category);
		return modelMapper.map(savedCategory, CategoryResponse.class);
	}

	@Override
	public CategoryResponse getCategoryById(Long id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
		return modelMapper.map(category, CategoryResponse.class);
	}

	@Override
	public List<CategoryResponse> getAllCategories() {
		List<Category> list = categoryRepository.findAll();
		return list.stream().map((category) -> modelMapper.map(category, CategoryResponse.class))
				.collect(Collectors.toList());
	}

	@Override
	public CategoryResponse updateCategory(CategoryResponse categoryDto, Long id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
		category.setDescription(categoryDto.getDescription());
		category.setName(categoryDto.getName());
		category.setId(id);
		Category updatedCategory = categoryRepository.save(category);
		return modelMapper.map(updatedCategory, CategoryResponse.class);
	}

	@Override
	public void deleteCategory(Long id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
		categoryRepository.delete(category);
	}

}
