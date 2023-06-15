package com.blog.restapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.restapi.payload.response.CategoryResponse;
import com.blog.restapi.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/blog/v1/categories")
@Tag(name = "CRUD Rest API for Category Resource")
public class CategoryController {

	private CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		super();
		this.categoryService = categoryService;
	}

	@Operation(summary = "Create Category Rest API", description = "Create Category Rest API is used to save Category into database")
	@ApiResponse(responseCode = "201", description = "Http Status 201 Created")
	@SecurityRequirement(name = "Bear Authentication")
	@PostMapping
	public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryResponse categoryDto) {
		return new ResponseEntity<CategoryResponse>(categoryService.addCategory(categoryDto), HttpStatus.CREATED);
	}
	
	@Operation(summary = "Get Category Rest API", description = "Get Category Rest API is used to get Category from database")
	@ApiResponse(responseCode = "200", description = "Http Status 200 Success")
	@GetMapping("/{id}")
	public ResponseEntity<CategoryResponse> getCategory(@PathVariable("id") Long id) {
		return ResponseEntity.ok(categoryService.getCategoryById(id));
	}

	@Operation(summary = "Get All Categegories Rest API", description = "Get All Categories Rest API is used to get categories from database")
	@ApiResponse(responseCode = "200", description = "Http Status 200 Success")
	@GetMapping
	public ResponseEntity<List<CategoryResponse>> getAllCategories() {
		return ResponseEntity.ok(categoryService.getAllCategories());
	}

	@Operation(summary = "Update Category Rest API", description = "Update Category Rest API is used to update category in database")
	@ApiResponse(responseCode = "200", description = "Http Status 200 Success")
	@SecurityRequirement(name = "Bear Authentication")
	@PutMapping("{id}")
	public ResponseEntity<CategoryResponse> updateCategory(@RequestBody CategoryResponse categoryDto,
														   @PathVariable("id") Long id) {
		return new ResponseEntity<CategoryResponse>(categoryService.updateCategory(categoryDto, id), HttpStatus.OK);
	}

	@Operation(summary = "Delete Category Rest API", description = "Delete Category Rest API is used to remove category from database")
	@ApiResponse(responseCode = "200", description = "Http Status 200 Success")
	@SecurityRequirement(name = "Bear Authentication")
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id) {
		categoryService.deleteCategory(id);
		return new ResponseEntity<String>("Category deleted successfully.", HttpStatus.OK);
	}
}
